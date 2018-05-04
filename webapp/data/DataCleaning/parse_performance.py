import sys

def main():

    read_file = open(sys.argv[1])
    venue_dictionary = {}
    conductor_dictionary = {}
    piece_dictionary = {}
    soloist_dictionary = {}
    load_dictionary(sys.argv[2], venue_dictionary)
    load_dictionary(sys.argv[3], conductor_dictionary)
    load_dictionary(sys.argv[4], piece_dictionary)
    load_dictionary(sys.argv[5], soloist_dictionary)
    count = 0
    previous_date = ''
    previous_piece = ''
    for line in read_file:
        line = line.rstrip()
        lineArray = line.split("@")
        date = lineArray[0]
        venue = lineArray[2].replace('"','')
        conductor = lineArray[4].replace('"','')
        piece = lineArray[5].replace('"','')
        soloist = lineArray[7].replace('"','')

        if date != previous_date or piece != previous_piece:
            count += 1
            previous_date = date
            previous_piece = piece
        try:
            performance = str(count) + "," + date + "," + venue_dictionary[venue] + "," + conductor_dictionary[conductor] + "," + piece_dictionary[piece] + "," + soloist_dictionary[soloist]
        except:
            pass
        print(performance)


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
