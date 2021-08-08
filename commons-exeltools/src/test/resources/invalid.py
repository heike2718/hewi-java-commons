read_file = pd.read_excel ("/home/heike/mkv/py-tests/klassenliste-iso.xlsx")

read_file.to_csv ("/home/heike/mkv/py-tests/klassenliste-0a8732c8.csv",
                  index = None,
                  header=True,
                  encoding='utf-8')
