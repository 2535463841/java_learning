# 1 工具说明

**工具的使用方法：**

	Usage: action [options]
	the value of action :
	     AutoClick 自动点击窗口按钮，并且保存窗口截图
	    RandomWordsCreater 创建随机单词


## 1.1 RandomWordsCreater

**用法**

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

例如，创建100行，每行 10列的单词，单词最短 5 个字符，最长 6 个字符，并且保存到 words.txt 文件中：

    java -jar demo.jar RandomWordsCreater -m 5 -M 6 -r 100 -c 10 -o words.txt

## 1.2 AutoClick

**用法**

	$ java -jar /f/demo.jar AutoClick -h
	usage: Usage of RandomWordsCreater
	 -c,--config-file <arg>   the path of config file, default is
	                          ./autoclick.properties
	 -h,--help                print help message

配置文件格式如下：

	window.names = 窗口名1, 窗口名2, ...
	window.<窗口名x>.clickpoints = 窗口内横坐标1:窗口内纵坐标1:<截图1保存的文件后缀名>, 窗口内横坐标2:窗口内纵坐标2:<截图2保存的文件后缀名>,  ....

> Note1：配置<窗口名x>时， 窗口名如果有空格，把空格替换为_  
> Note2：如果不需要截图，不需要配置截图文件后缀名  
> Note3：截图保存到执行命令的当前目录下  

例如：./autoclick.properties 内容如下：

	window.names=ESET Internet Security
	window.ESET_Internet_Security.clickpoints = 120:140:home,120:180

执行如下命令， 将显示ESET Internet Security 窗口，点击(120,140) 保存截图, 点击(120,180) 不保存截图

	$ java -jar /f/demo.jar AutoClick -c ./autoclick.properties

生成的文件如下：

	$ ls *.png
	2019-10-02_143543_ESET_Internet_Security_home.png

 
 
 
 
 