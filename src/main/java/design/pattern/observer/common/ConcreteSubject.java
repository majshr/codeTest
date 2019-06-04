package design.pattern.observer.common;
/**
 * 具体主题
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月25日 下午2:33:06
 */
public class ConcreteSubject extends Subject{
	/**
	 * 主题状态
	 */
	private String subjectState;

	/**
	 * 获取主题状态
	 * @return String 
	 * @date: 2019年2月12日 下午2:34:58
	 */
	public String getSubjectState() {
		return subjectState;
	}

	public void setSubjectState(String subjectState) {
		this.subjectState = subjectState;
	}
	
}
