
public class main {

	public static void main(String[]args){
		double frequenc=16.3;
		double duration=1/frequenc*1000;
		double UnitTimeMS=1;
		double times=duration/UnitTimeMS;
		System.out.println((int)times);
		for(int i=0;i<=(int)times;i++){
			int n=(int) (122*Math.sin(2*Math.PI*i/times)+122);
			System.out.print(n+",");
		}
	}
}
