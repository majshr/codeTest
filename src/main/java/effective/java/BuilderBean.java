package effective.java;

public class BuilderBean {
	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;
	
	public static class Builder{
		private int servingSize;
		private int servings;
		private int calories;
		private int fat;
		private int sodium;
		private int carbohydrate;
		
		public Builder(int servingSize, int serservings) {
			this.servingSize = servingSize;
			this.servings = serservings;
		}
		
		public Builder calories(int val) {
			this.calories = val;
			return this;
		}
		
		public Builder sodium(int val) {
			this.sodium = val;
			return this;
		}
		
		public Builder carbohydrate(int val) {
			this.carbohydrate = val;
			return this;
		}
		
		public Builder fat(int val) {
			this.fat = val;
			return this;
		}
		
		public BuilderBean build() {
			return new BuilderBean(this);
		}
	}
	
	private BuilderBean(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;		
	}
	
	public static void main(String[] args) {
		// 创建对象方式
		BuilderBean bean = new Builder(100, 10).calories(10).fat(100).build();
	}
}
