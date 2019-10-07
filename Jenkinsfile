@Grab(group = 'net.sf.opencsv', module = 'opencsv', version = '2.3')
import au.com.bytecode.opencsv.CSVReader
import au.com.bytecode.opencsv.CSVParser
import au.com.bytecode.opencsv.CSVWriter

def TEST_FILE_NAME = 'Book1.csv'
def TEST_OUTPUT_FILE_NAME = 'testOut.csv'

List<String[]> rows = new CSVReader(new FileReader(new File(TEST_FILE_NAME)), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_ESCAPE_CHARACTER, CSVParser.DEFAULT_QUOTE_CHARACTER, 0).readAll()
//def Emp_Dict = [:]
def Emp_list =[]
def Emp_details=[:]

	for(i=1;i<rows.size();i++) {
		def Emp_Dict = [:]
		for(j=0;j<rows[i].size();j++) {
			Emp_Dict[rows[0][j]]=rows[i][j]}
			//println(rows[i][j])
		Emp_details[rows[i][0]]=Emp_Dict
		
	}
			
	print Emp_details['123']['Mobile_No']
