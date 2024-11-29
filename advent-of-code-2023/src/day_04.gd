extends SceneTree

var util = preload("res://src/util.gd").new()

func _init():
	var inputs = util.read_input("day_04.txt")
	part_one(parse_input(inputs))
	part_two(parse_input(inputs))

func part_one(input: Array):
	var result = 0
	for line in input:
		var win = calculate_winning_card(line[0], line[1])
		if (win > 0):
			result += pow(2, win - 1)
	
	print(result)
	
func part_two(input: Array):
	var size = input.size()
	var instances = []
	instances.resize(size)
	instances.fill(1)

	for x in range(size):
		var win = calculate_winning_card(input[x][0], input[x][1])
		var y = x + 1
		while y < size && win > 0:
			instances[y] = instances[y] + instances[x]
			y += 1
			win -= 1

	print(instances.reduce(func(accum, number): return accum + number))

func parse_input(inputs: Array) -> Array:
	var regex = RegEx.new()
	regex.compile("\\d+")
	var result = []
	for line in inputs:
		var game = (line.split(": ")[1]).split(" | ")
		var winning_card = regex.search_all(game[0]).map(func(x): return x.get_string().to_int())
		winning_card.sort()
		var owned_numbers = regex.search_all(game[1]).map(func(x): return x.get_string().to_int())
		owned_numbers.sort()
		
		result.append([winning_card, owned_numbers])
		
	return result


func calculate_winning_card(winning_card: Array, owned_numbers: Array) -> int:
	var i = 0
	var j = 0
	var len_i = winning_card.size()
	var len_j = owned_numbers.size()
	var win = 0
	while (i < len_i && j < len_j):
		if winning_card[i] == owned_numbers[j]:
			win += 1
			i += 1
			j += 1
		elif winning_card[i] < owned_numbers[j]:
			i += 1
		else:
			j += 1
	
	return win
