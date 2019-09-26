# 1 Examples:


## 1.1 RandomWordsCreater

Usage:

	usage: Usage of RandomWordsCreater
	 -c,--column <arg>      the column num of words to create(default is 10)
	 -h,--help              print help message
	 -m,--min-lenth <arg>   the min length of word
	 -M,--max-lenth <arg>   the max length of word
	 -o,--output <arg>      save to output file, if not set, print to
	                        console,(default is not set)
	 -r,--row <arg>         the row num of words to create(default is 100)
	 -s,--split <arg>       the split string in words(default is ' ')


command:

    java -cp demo.jar org.zbw.utils.RandomWordsCreater -m 5 -M 6 -r 100000 -c 300 -o word.txt
