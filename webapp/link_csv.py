import sys

def main():
    read_file = open(sys.argv[1])
    file1_ids_path = sys.argv[2]
    file2_ids_path = sys.argv[3]
    not_found = []
    for line in read_file:
        dictionary1 = {}
        dictionary2 = {}
        load_dictionary(file1_ids_path, dictionary1)
        load_dictionary(file2_ids_path, dictionary2)

        field1_start = line.index("\"") + 1
        field1_end = field1_start + line[field1_start:].index("\"")
        field2_start = field1_end + line[field1_end+1:].index("\"") + 2 
        field2_end = field2_start + line[field2_start:].index("\"")
        field1 = line[field1_start:field1_end]
        field2 = line[field2_start:field2_end]        
       #lineArray = line.split("@")
        #field1 = lineArray[0]
        #field2 = lineArray[1]
        link = dictionary1[field1] + "," + dictionary2[field2]
        print(link)



def load_dictionary(data_file_path, dictionary):
    data_file = open(data_file_path)
    count = 0
    for line in data_file:
        count += 1
        field_start = line.index("\"") + 1
        field_end = field_start + line[field_start:].index("\"")
        field = line[field_start:field_end]
        dictionary[field] = line[0:line.index(",")]
    
    data_file.close()


main()
