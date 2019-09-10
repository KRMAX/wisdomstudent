package net.huawei.wisdomstudy.cons;


/**
 *整个应用通用的常量 
 *<br><b>类描述:</b>
 *<pre>|</pre>
 *@see
 *@since
 */
public class CommonConstant
{
   /**
    * 用户对象放到Session中的键名称
    */
   public static final String USER_CONTEXT = "USER_CONTEXT";
   
   /**
    * 将登录前的URL放到Session中的键名称
    */
   public static final String LOGIN_TO_URL = "/login";
   
   /**
    * 每页的记录数
    */
   public static final int PAGE_SIZE = 3;
   
   /**
    * 登录系统的角色：1为管理员，2为教师，3为学生
    */
   public static final byte USER_ROLE_TEACHER = 2;
   public static final byte USER_ROLE_STUDENT = 3;
   public static final String ROLE_TEACHER = "teacher";
   public static final String ROLE_STUDENT = "student";
   
   /**
    * 用户的初始密码：123456
    */
   public static final String INITIAL_PASSWORD = "123456";
   
   public static final String FIELD_GAODENGSHUXUE = "高等数学";
   public static final String FIELD_XIANXINGDAISHU = "线性代数";
   public static final String FIELD_GAILVLUN = "概率论与数理统计";
   
   /**
    * combobox下拉框的"全部"选项
    * added by cexo on 2016-4-14
    */
   public static final String SEARCH_CONDITION_ALL = "--全部--";
   
   public static final String SINGLE_OPTION = "单选题";
   public static final String DOUBLE_OPTION = "双选题";
   public static final String MUTI_OPTION = "多选题";
   public static final String TRUE_FALSE = "判断题";
   /**
    * 知识点树结构的层次划分
    * addedy by cexo on 2017-12-13
    */
   public static final String KP_TREE_ROOT_LEVEL = "根节点";
   public static final String KP_TREE_ROOT_TEXT = "全部";
   public static final String KP_TREE_COURSE_LEVEL = "课程";
   public static final String KP_TREE_CHAPTER_LEVEL = "章节";
   public static final String KP_TREE_KNOWLEDGE_LEVEL = "知识点";
}
