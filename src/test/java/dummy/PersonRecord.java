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

	// ID NAME SALARY AGE
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", id);
		map.put("NAME", name);
		map.put("SALARY", salary);
		map.put("AGE", age);

		return map;
		
//		return new BeanMap(this);
	}
};
