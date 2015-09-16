package generator;

import org.mybatis.generator.api.ShellRunner;

/**
 * Hello world!
 *
 */
public class Generator
{
    public static void main( String[] args )
    {
    	String [] argument=new String[3];
    	argument[0]="-configfile";
//    	argument[1]="D://work//test//mybatis-generator//src//test//java//generator//generator.xml";
    	argument[1]=".//src/test//java//generator//generator-1.xml";
    	argument[2]="-overwrite";
    	ShellRunner.main(argument);
    }
}
