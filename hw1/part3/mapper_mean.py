import sys

# Стандартный инпут
for line in sys.stdin:
	row = line.split(',')
	try:
		# Зная структуру данных, точно не наткнемся на лишние запятые, 
		# если возьмем 7й столбец справа
		price = int(row[-7])
	except:
		continue
	# В price лежит среднее одного наблюдения, поэтому выдаем 1	price
	print(f'1\t{price}')
