package net.huawei.wisdomstudy.service.inter;

import java.io.File;
import java.util.Map;

public interface IExcelImportService {

	public Map<String, Object> importQuestionExcel(File file, String knowledgePointDesc, String chapterName,
			int questTypeId,String questType, String field, int kpId, String saveDirectoryPath);
}
