import sys

def main():
    read_file = open(sys.argv[1])
    field_number = int(sys.argv[2])
    for line in read_file:
        line = line.rstrip()
        lineArray = line.split(",")
        try:
            data = lineArray[0] + "," + lineArray[field_number]
        except:
            pass
        print(data)

main()
