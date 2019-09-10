package net.huawei.wisdomstudy.service.inter;

import net.huawei.wisdomstudy.domain.HomeworkidClassId;

import java.util.List;

public interface IHCService {
    public List<HomeworkidClassId> getHomeworkidClazzidByClazzId(int clazzid);

}
