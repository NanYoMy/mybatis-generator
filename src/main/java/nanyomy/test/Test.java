package nanyomy.test;

import org.mybatis.generator.api.ShellRunner;

/**
 * Hello world!
 *
 */
public class Test
{
    public static void main( String[] args )
    {
    	String [] argument=new String[3];
    	argument[0]="-configfile";
    	argument[1]="D:\\work\\test\\mybatisgenerator2\\src\\test\\java\\resource\\generator.xml";
//    	argument[1]="./mybatisgenerator2/src/test/java/resource/generator.xml";
    	argument[2]="-overwrite";
    	ShellRunner.main(argument);
    	
    }
}
