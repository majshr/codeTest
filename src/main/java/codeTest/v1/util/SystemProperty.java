package codeTest.v1.util;

/**
 * ϵͳ��������
 * @author maj
 *
 */
public class SystemProperty {
	public static void main(String[] args) {
		System.out.println("java�汾�ţ�" + System.getProperty("java.version")); // java�汾��
		System.out.println("Java�ṩ�����ƣ�" + System.getProperty("java.vendor")); // Java�ṩ������
		System.out.println("Java�ṩ����վ��" + System.getProperty("java.vendor.url")); // Java�ṩ����վ
		System.out.println("jreĿ¼��" + System.getProperty("java.home")); // Java��Ŷ��Ӧ����jreĿ¼
		System.out.println("Java������淶�汾�ţ�" + System.getProperty("java.vm.specification.version")); // Java������淶�汾��
		System.out.println("Java������淶�ṩ�̣�" + System.getProperty("java.vm.specification.vendor")); // Java������淶�ṩ��
		System.out.println("Java������淶���ƣ�" + System.getProperty("java.vm.specification.name")); // Java������淶����
		System.out.println("Java������汾�ţ�" + System.getProperty("java.vm.version")); // Java������汾��
		System.out.println("Java������ṩ�̣�" + System.getProperty("java.vm.vendor")); // Java������ṩ��
		System.out.println("Java��������ƣ�" + System.getProperty("java.vm.name")); // Java���������
		System.out.println("Java�淶�汾�ţ�" + System.getProperty("java.specification.version")); // Java�淶�汾��
		System.out.println("Java�淶�ṩ�̣�" + System.getProperty("java.specification.vendor")); // Java�淶�ṩ��
		System.out.println("Java�淶���ƣ�" + System.getProperty("java.specification.name")); // Java�淶����
		System.out.println("Java��汾�ţ�" + System.getProperty("java.class.version")); // Java��汾��
		System.out.println("Java��·����" + System.getProperty("java.class.path")); // Java��·��
		System.out.println("Java lib·����" + System.getProperty("java.library.path")); // Java lib·��
		System.out.println("Java���������ʱ·����" + System.getProperty("java.io.tmpdir")); // Java���������ʱ·��
		System.out.println("Java��������" + System.getProperty("java.compiler")); // Java������
		System.out.println("Javaִ��·����" + System.getProperty("java.ext.dirs")); // Javaִ��·��
		System.out.println("����ϵͳ���ƣ�" + System.getProperty("os.name")); // ����ϵͳ����
		System.out.println("����ϵͳ�ļܹ���" + System.getProperty("os.arch")); // ����ϵͳ�ļܹ�
		System.out.println("����ϵͳ�汾�ţ�" + System.getProperty("os.version")); // ����ϵͳ�汾��
		System.out.println("�ļ��ָ�����" + System.getProperty("file.separator")); // �ļ��ָ���
		System.out.println("·���ָ�����" + System.getProperty("path.separator")); // ·���ָ���
		System.out.println("ֱ�߷ָ�����" + System.getProperty("line.separator")); // ֱ�߷ָ���
		System.out.println("����ϵͳ�û�����" + System.getProperty("user.name")); // �û���
		System.out.println("����ϵͳ�û�����Ŀ¼��" + System.getProperty("user.home")); // �û�����Ŀ¼
		System.out.println("��ǰ��������Ŀ¼��" + System.getProperty("user.dir")); // ��ǰ��������Ŀ¼
	}
}
