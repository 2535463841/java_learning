# 1 ����˵��

**���ߵ�ʹ�÷�����**

	Usage: action [options]
	the value of action :
	     AutoClick �Զ�������ڰ�ť�����ұ��洰�ڽ�ͼ
	    RandomWordsCreater �����������


## 1.1 RandomWordsCreater

**�÷�**

	$ java -jar demo.jar RandomWordsCreater
	usage: Usage of RandomWordsCreater
	 -c,--column <arg>      the column num of words to create(default is 10)
	 -h,--help              print help message
	 -m,--min-lenth <arg>   the min length of word
	 -M,--max-lenth <arg>   the max length of word
	 -o,--output <arg>      save to output file, if not set, print to
	                        console,(default is not set)
	 -r,--row <arg>         the row num of words to create(default is 100)
	 -s,--split <arg>       the split string in words(default is ' ')

���磬����100�У�ÿ�� 10�еĵ��ʣ�������� 5 ���ַ���� 6 ���ַ������ұ��浽 words.txt �ļ��У�

    java -jar demo.jar RandomWordsCreater -m 5 -M 6 -r 100 -c 10 -o words.txt

## 1.2 AutoClick

**�÷�**

	$ java -jar /f/demo.jar AutoClick -h
	usage: Usage of RandomWordsCreater
	 -c,--config-file <arg>   the path of config file, default is
	                          ./autoclick.properties
	 -h,--help                print help message

�����ļ���ʽ���£�

	window.names = ������1, ������2, ...
	window.<������x>.clickpoints = �����ں�����1:������������1:<��ͼ1������ļ���׺��>, �����ں�����2:������������2:<��ͼ2������ļ���׺��>,  ....

> Note1������<������x>ʱ�� ����������пո񣬰ѿո��滻Ϊ_  
> Note2���������Ҫ��ͼ������Ҫ���ý�ͼ�ļ���׺��  
> Note3����ͼ���浽ִ������ĵ�ǰĿ¼��  

���磺./autoclick.properties �������£�

	window.names=ESET Internet Security
	window.ESET_Internet_Security.clickpoints = 120:140:home,120:180

ִ��������� ����ʾESET Internet Security ���ڣ����(120,140) �����ͼ, ���(120,180) �������ͼ

	$ java -jar /f/demo.jar AutoClick -c ./autoclick.properties

���ɵ��ļ����£�

	$ ls *.png
	2019-10-02_143543_ESET_Internet_Security_home.png

 
 
 
 
 