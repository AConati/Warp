import sys

read_file = open(sys.argv[1])
count = 1
for line in read_file:
    line = line.rstrip()
   # line = line[line.index(",") + 1:]
    line = str(count) +  "," + line 
    count += 1
    print(line)


