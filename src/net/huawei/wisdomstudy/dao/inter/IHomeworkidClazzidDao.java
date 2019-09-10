package net.huawei.wisdomstudy.dao.inter;


import net.huawei.wisdomstudy.domain.HomeworkidClassId;

import java.util.List;

public interface IHomeworkidClazzidDao {

    public List<HomeworkidClassId> getHomeworkidClazzidByClazzId(int clazzid);
    public List<HomeworkidClassId> getHomeworkidClazzidByHomeworkId(int homeworkid);
    public List<HomeworkidClassId> getHomeworkClazz(int clazzid,int homeworkid, int firstResult, int maxResults);
}
