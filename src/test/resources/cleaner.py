def filter_lines(input_file, output_file):
    try:
        with open(input_file, 'r') as infile, open(output_file, 'w') as outfile:
            for line in infile:
                # The rstrip() method removes any trailing whitespace, including the newline character
                if line.lstrip().startswith('1'):
                    outfile.write(line)
                    outfile.write("\n")
                    
    except FileNotFoundError:
        print(f"Error: The file '{input_file}' was not found.")
    except Exception as e:
        print(f"An error occurred: {e}")

def filter_extra_info(input_file,output_file):
    t = 0
    try:
        with open(input_file, 'r') as infile, open(output_file, 'w') as outfile, open("o3.pgn","w") as o3:
            for line in infile:
                if(len(line)>1):
                    arr = line.split(" ")
                    risArr =[]
                    ris2 = ""
                    skip = False
                    for x in range(0,len(arr)):
                        if(arr[x] == "{"):
                            skip = True
                        elif(arr[x] == "}"):
                            skip = False
                        if(skip):
                            continue
                        if(arr[x][0] in ("a","b","c","d","e","f","g","h","B","N","R","K","Q","O")
                           and arr[x][-1] != "."):
                            while(arr[x][-1] in ("?!")):
                                arr[x] = arr[x][:-1]
                            risArr.append("\""+arr[x]+"\",")
                            ris2+=" "+arr[x]
                    ris = "".join(risArr)
                    outfile.write("@Test\n")
                    outfile.write(f"public void test{t}()\n")
                    outfile.write("{\n")
                    outfile.write("\tcorrectMovesTest(List.of("+ris[:len(ris)-1]+"));\n}\n")
                    o3.write(ris2+"\n\n")
                    t+=1
    except Exception as e:
        print(f"An error occurred: {e}")


if(__name__ == "__main__"):
    filter_extra_info("o.pgn","o2.pgn")
