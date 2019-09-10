package net.huawei.wisdomstudy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据字典POJO
 * @author cexo added on 2018-10-31
 * 唯一标示OID:id
 * 类型代码:dicType
 * 类型名称:dicTypeName
 * 项目代码:itemCode
 * 项目名称:itemName
 * 排序值，最大值为默认值:itemSortValue
 */
@Entity
@Table(name = "t_dictionary", catalog = "wisdomstudy")
public class Dictionary implements Serializable{

	private static final long serialVersionUID = -5679112062325160403L;

	private int id;
	
	private String dicType;
	
	private String dicTypeName;
	
	private String itemCode;
	
	private String itemName;
	
	private int itemSortValue;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	@Column(name = "dic_type_code", nullable = false)
	public String getDicType() {

		return dicType;
	}

	public void setDicType(String dicType) {

		this.dicType = dicType;
	}

	@Column(name = "dic_type_name", nullable = false)
	public String getDicTypeName() {

		return dicTypeName;
	}

	public void setDicTypeName(String dicTypeName) {

		this.dicTypeName = dicTypeName;
	}

	@Column(name = "item_code", nullable = false)
	public String getItemCode() {

		return itemCode;
	}

	public void setItemCode(String itemCode) {

		this.itemCode = itemCode;
	}

	@Column(name = "item_name", nullable = false)
	public String getItemName() {

		return itemName;
	}

	public void setItemName(String itemName) {

		this.itemName = itemName;
	}

	@Column(name = "item_sort_value", nullable = false)
	public int getItemSortValue() {

		return itemSortValue;
	}

	public void setItemSortValue(int itemSortValue) {

		this.itemSortValue = itemSortValue;
	}
	
	
	
}
