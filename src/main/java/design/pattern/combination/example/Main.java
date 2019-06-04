package design.pattern.combination.example;

public class Main {
	public static void main(String[] args) {
		
		Company c = new ConcreteCompany("总公司");
		c.add(new HRDepartment("总公司_hr_a"));
		c.add(new FinanceDepartment("总公司_caiwu_b"));
		
		// 华东分公司
		Company fdc = new ConcreteCompany("华东分公司");
		fdc.add(new HRDepartment("华东分公司_hr_a"));
		fdc.add(new FinanceDepartment("华东分公司_caiwu_b"));
		c.add(fdc);
		
		// 华北分公司
		Company fbc = new ConcreteCompany("华北分公司");
		fbc.add(new HRDepartment("华北分公司_hr_a"));
		fbc.add(new FinanceDepartment("华北分公司_caiwu_b"));
		c.add(fbc);
		
		c.display(1);
	}
}
