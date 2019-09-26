# 1 工具说明

## 1.1 RandomWordsCreater

**说明：创建随机单词**

用法：

	usage: Usage of RandomWordsCreater
	 -c,--column <arg>      the column num of words to create(default is 10)
	 -h,--help              print help message
	 -m,--min-lenth <arg>   the min length of word
	 -M,--max-lenth <arg>   the max length of word
	 -o,--output <arg>      save to output file, if not set, print to
	                        console,(default is not set)
	 -r,--row <arg>         the row num of words to create(default is 100)
	 -s,--split <arg>       the split string in words(default is ' ')


例如，创建100行，每行 10列的单词，单词最短 5 个字符，最长 6 个字符，并且保存大 words.txt 文件中：

    java -cp demo.jar org.zbw.utils.RandomWordsCreater -m 5 -M 6 -r 100 -c 10 -o words.txt
