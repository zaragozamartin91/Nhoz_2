package dummy;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;

public class PersonRecord {
	private Integer id;
	private String name;
	private Double salary;
	private Integer age;

	public PersonRecord(Integer id, String name, Double salary, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getSalary() {
		return salary;
	}

	public Integer getAge() {
		return age;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	// ID NAME SALARY AGE
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", id);
		map.put("NAME", name);
		map.put("SALARY", salary);
		map.put("AGE", age);

		return map;

		// return new BeanMap(this);
	}

	public PersonRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

};
