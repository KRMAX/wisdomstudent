package net.huawei.wisdomstudy.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jxl.Cell;
import jxl.Image;
import jxl.JXLException;
import jxl.Sheet;
import jxl.Workbook;
import net.huawei.wisdomstudy.cons.CommonConstant;
import net.huawei.wisdomstudy.cons.State;
import net.huawei.wisdomstudy.dao.inter.IQuestionDao;
import net.huawei.wisdomstudy.domain.Question;
import net.huawei.wisdomstudy.security.UserInfo;
import net.huawei.wisdomstudy.service.inter.IExcelImportService;

@Service
@Transactional
public class ExcelImportServiceImpl implements IExcelImportService {

	@Autowired
	IQuestionDao questDao;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final int FIRST_ROW_LENGTH = 4;

	@Override
	public Map<String, Object> importQuestionExcel(File file, String knowledgePointDesc, String chapName,
			int questTypeId,String questType, String field,int kpId, String saveDirectoryPath) {
		
		Map<String, Object> map = null;
		try {
			map = readQuestExcel(file, knowledgePointDesc, chapName,questTypeId,questType,
						field,kpId,saveDirectoryPath);
			System.out.println("读取结果为" + map);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			System.out.println(e.getCause());
			map = new HashMap<String, Object>();
			map.put("code", "2000");
			String message = "传入文件时发生异常，上传失败！";
			map.put("message", message);
		}
		return map;
	}

	// 读取习题excel文件
	private Map<String, Object> readQuestExcel(File file,String knowledgePointDesc,String chapName,
			int questTypeId,String questType,String field,int kpId,String saveDirectoryPath)
			throws JXLException, IOException, NumberFormatException {

		logger.debug("==========ExcelImportServiceImpl---readQuestExcel() [begin]==========");
		Map<String, Object> map = new HashMap<String, Object>();
		String message = "";

		// Excel工作薄对象
		// Workbook book = Workbook.getWorkbook(new File(excelPath));
		Workbook book = Workbook.getWorkbook(file);
		// 第一张工作表对象
		Sheet sheet = book.getSheet(0);
		// 判断读取sheet的行始位置
		int sheetCounts = sheet.getRows();
		if (sheetCounts <= 1) {
			System.out.println("传入的excel文件包含行数据<=1，上传失败！");
			message = "传入的excel文件包含行数据<=1，上传失败！";
			// 自定义code = 2001 表示传人的excel文件表头有问题，上传失败！
			map.put("code", "2001");
			map.put("message", message);
			//最后关闭资源，释放内存
			book.close();
			return map;
		}
		Cell[] firstRow = sheet.getRow(1);// 取出第二行
		if (firstRow.length != FIRST_ROW_LENGTH)// 如果第二行数据不全
		{
			System.out.println("传入的excel文件表头数量不符");
			message = "传入的excel文件表头数量不符";
			map.put("code", "2001");
			map.put("message", message);
			//最后关闭资源，释放内存
			book.close();
			return map;
		}

		// 取出题目有关的描述信息
		String fieldName = firstRow[0].getContents();
		String chapterName = firstRow[1].getContents();
		String knowledgeDesc = firstRow[2].getContents();
		String questionType = firstRow[3].getContents();
		System.out.println("取出题目有关的描述信息:fieldName=" + fieldName + "chapterName=" + chapterName +
				"knowledgeDesc="+knowledgeDesc + "questionType=" + questionType);
		//将表头与select的上传条件比较，不相等就是非法文件
		if(!knowledgePointDesc.equals(knowledgeDesc) || !chapName.equals(chapterName) ||
				!questType.equals(questionType) || !field.equals(fieldName) ){
			return State.NO_CONFORM_TEMPLATE.toMap();
		}		

		List<Question> questionList = new ArrayList<Question>();
		
		for (int i = 0; i < sheet.getNumberOfImages(); i++) {	
			System.out.println("i = " + i);
			Image image = sheet.getDrawing(i);
			if (image != null) {
				System.out.println("image.getRow()--->" + image.getRow());
				int key = (int) image.getRow();// 拿到图片所在的行索引
				System.out.println("key--->" + key);
				byte[] imageData = image.getImageData();// 拿到該行圖片的字節數據
				System.out.println("imageData.length = " + imageData.length);

				Calendar calendar =Calendar.getInstance();
				int year=calendar.get(Calendar.YEAR); 
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DATE);
				int hour = calendar.get(Calendar.HOUR);
				int minute = calendar.get(Calendar.MINUTE);
				int second = calendar.get(Calendar.SECOND);
				String fileName =  year+"-"+month+"-"+day+"-"+hour+minute+second+i+".png";
				try{ 
				    FileImageOutputStream imageOutput = new FileImageOutputStream(new File(saveDirectoryPath,fileName));  
				    imageOutput.write(imageData, 0, imageData.length);  
				    imageOutput.close();
				    
				    } catch(Exception ex) {  
				      System.out.println("Exception: " + ex);  
				      ex.printStackTrace();  
				    } 
				// 获取答案
				Cell cell = sheet.getCell(1, key);// 定义第二列为对应题的答案
				StringBuilder answer = new StringBuilder();//准备存入数据库的答案
				
				if (questType.equals(CommonConstant.SINGLE_OPTION)){
					if(cell.getContents() == null || cell.getContents().equals("")){
						message = "有的题目没有答案！";
						map.put("code", "500");
						map.put("message", message);
						return map;
					}
					// 如果答案是小写，转换成大写
					char ans = cell.getContents().charAt(0);
					if (ans >= 'a' && ans <= 'z') {
						ans = (char) (ans - 32);
					}
					answer.append(ans);
				}else if(questType.equals(CommonConstant.MUTI_OPTION)){
					if(cell.getContents() == null || cell.getContents().equals("")
							|| cell.getContents().length() <=1){
						message = "有的题目没有答案！";
						map.put("code", "500");
						map.put("message", message);
						return map;
					}
					// 如果答案是小写，转换成大写
					for(char ans:cell.getContents().toCharArray()){
						if (ans >= 'a' && ans <= 'z') {
							ans = (char) (ans - 32);
						}
						answer.append(ans);
					}
				}
				// 获取答案
				Cell cell2 = sheet.getCell(2, key);// 定义第三列为对应题的描述
					
				Question question = new Question();
				question.setAnswer(answer.toString());
				System.out.println(saveDirectoryPath);
				question.setContent("{\"title\":\"\",\"titleImg\":\"" + saveDirectoryPath + "/" + fileName + "\"}");
				//question.setContent(saveDirectoryPath + "/" + fileName);
				System.out.println("question.content = " + question.getContent());
				question.setQuestionDesc(cell2.getContents());
				UserInfo userDetails = (UserInfo) SecurityContextHolder.getContext()
						    .getAuthentication()
						    .getPrincipal();
				question.setCreator(userDetails.getTrueName());
				questionList.add(question);									
			} else {
				break;
			}
		} // end for(int i=0;i<sheet.getNumberOfImages();i++)
		questDao.addQuestion(questionList,questTypeId, kpId);
		//最后关闭资源，释放内存
		book.close();
		logger.debug("==========ExcelImportServiceImpl---readQuestExcel() [end]==========");
		message = "上传成功！";
		map.put("code", "200");
		map.put("message", message);
		return map;

	}
	
}
