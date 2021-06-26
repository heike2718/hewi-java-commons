#importing pandas as pd
import pandas as pd

# Read and store content
# of an excel file
#read_file = pd.read_excel ("Test.xlsx")
#read_file = pd.read_excel ("auswertung-ohne-intro.xlsx")
read_file = pd.read_excel ("klassenliste-iso.xlsx")
#read_file = pd.read_excel ("/media/veracrypt1/Strato/minikaenguru/2018-mkv/done/auswertung.xls")


# Write the dataframe object
# into csv file
read_file.to_csv ("Test.csv",
                  index = None,
                  header=True,
                  encoding='utf-8')
#read_file.to_csv ("Test.csv",
#                  index = None,
#                  header=True)

# read csv file and convert
# into a dataframe object
df = pd.DataFrame(pd.read_csv("Test.csv"))

# show the dataframe
print(df)
