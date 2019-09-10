package net.huawei.wisdomstudy.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jxl.Cell;
import jxl.JXLException;
import jxl.Sheet;
import jxl.Workbook;
import net.huawei.wisdomstudy.dao.inter.IClazzDao;
import net.huawei.wisdomstudy.domain.Clazz;
import net.huawei.wisdomstudy.service.inter.IClazzService;
import net.huawei.wisdomstudy.util.PropertyUtil;
import net.huawei.wisdomstudy.cons.CommonConstant;
import net.huawei.wisdomstudy.cons.State;

@Service
@Transactional
public class ClazzServiceImpl implements IClazzService{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IClazzDao clazzDao;
	
	@Override
	public void addClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		clazzDao.addClazz(clazz);
	}

	@Override
	public Clazz getClazz(int id) {
		// TODO Auto-generated method stub
		return clazzDao.getClazz(id);
	}

	@Override
	public void updateClazz(Clazz clazz) {
		// TODO Auto-generated method stub
		clazzDao.updateClazz(clazz);
	}

	@Override
	public Map<String, Object> getClazzes(int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getClazzesWithoutStu(Clazz clazz, int firstResult, int maxResults) {
		Map<String,Object> map = clazzDao.getClazzes(clazz, firstResult, maxResults);
		@SuppressWarnings("unchecked")
		List<Clazz> list = (List<Clazz>) map.get("rows");
		for(Clazz c:list){
			c.setStudent(null);
			
		}
		
		return clazzDao.getClazzes(clazz, firstResult, maxResults);
	}

	@Override
	public Map<String, Object> getClazzesWithoutStu(String role, String username, Clazz clazz, int firstResult, int maxResults) {
		if(role == CommonConstant.ROLE_TEACHER) {
			//根据用户名获取tearcher OID，再将教师名下的班级列表返回。
		}
		return null;
	}
	
	@Override
	public List<String> getMajorList() {
		List<String> majorList = clazzDao.getMajorList();
		return majorList;
	}

	@Override
	public List<String> getMajorList(String department) {
		
		return clazzDao.getMajorList(department);
	}
	
	@Override
	public List<String> getDepartmentList() {

		return clazzDao.getDepartmentList();
	}

	@Override
	public Map<String, Object> importClazzExcel(File file) {
		Map<String, Object> map = null;
		try {
			map = readClazzExcel(file);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			System.out.println(e.getCause());
			return State.ERROR.toMap();
		}
		return map;
		
	}

	// 读取习题excel文件
	private Map<String, Object> readClazzExcel(File file)
				throws JXLException, IOException, NumberFormatException {

		logger.debug("==========ClazzServiceImpl---readClazzExcel() [begin]==========");

		// Excel工作薄对象
		// Workbook book = Workbook.getWorkbook(new File(excelPath));
		Workbook book = Workbook.getWorkbook(file);
		// 第一张工作表对象
		Sheet sheet = book.getSheet(0);
		// 判断读取sheet的行始位置
		int sheetCounts = sheet.getRows();
		if (sheetCounts <= 1) {
			System.out.println("传入的excel文件包含行数据<=1，上传失败！");
			//最后关闭资源，释放内存
			book.close();
			return State.NULL_FILE.toMap();
		}
		Cell[] firstRow = sheet.getRow(0);// 取出第一行
		int clazzFirstRowLength = Integer.parseInt(PropertyUtil.getProperty("clazzFirstRowLength"));
		if (firstRow.length != clazzFirstRowLength){// 校验表头数量
			System.out.println("506:传入的excel文件表头与模板不符");
			//最后关闭资源，释放内存
			book.close();
			return State.HEADER_ERROR.toMap();
		}	
			
		for (int i = 1; i < sheet.getRows(); i++) {//从第二行开始
			System.out.println("i = " + i);
			Cell[] cells = sheet.getRow(i);
			
			String clazzName = cells[0].getContents();//第1列是班级名称
			String admissionYear = cells[1].getContents();//第2列是入学年度
			String major = cells[2].getContents();// 第3列是所学专业
			String department = cells[3].getContents();// 第4列是所属系
			Clazz clazz = new Clazz();
			clazz.setClazzName(clazzName);
			clazz.setAdmissionYear(admissionYear);
			clazz.setMajor(major);
			clazz.setDepartment(department);
			clazzDao.addClazz(clazz);
		} // end for(int i=0;i< sheet.getRows(); i++)
			
		//最后关闭资源，释放内存
		book.close();
		logger.debug("==========ClazzImportServiceImpl---readClazzExcel() [end]==========");
		return State.OK.toMap();

	}

	@Override
	public List<Clazz> getClazzList(String department, String admissionYear) {
		return clazzDao.getClazzList(department, admissionYear);
	}
}
