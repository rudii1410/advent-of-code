extends SceneTree

var util = preload("res://src/util.gd").new()

func _init():
	var inputs = util.read_input("day_06.txt")
	var parsed_input = parse_input(inputs)
	part_one(parsed_input)
	part_two(parsed_input)

func part_one(input: Array):
	var result = 1
	for i in input:
		result *= solution(i["time"], i["distance"])
	
	print(result)

func part_two(input: Array):
	var time = ""
	var distance = ""
	for i in input:
		time += ("%d" % i["time"])
		distance += ("%d" % i["distance"])
	
	print(solution(time.to_int(), distance.to_int()))

func solution(time: int, distance: int) -> int:
	var i = 1
	var min_time = -1
	while i < time:
		if (i * (time - i)) > distance:
			min_time = i
			break
		i += 1
	
	return time - (2 * min_time) + 1

func parse_input(inputs: Array) -> Array:
	var parsed_input = []
	var regex = RegEx.new()
	regex.compile("\\d+")
	var times = regex.search_all(inputs[0]).map(func(x): return x.get_string().to_int())
	var distance = regex.search_all(inputs[1]).map(func(x): return x.get_string().to_int())
	
	for i in range(times.size()):
		parsed_input.append({ "time": times[i], "distance": distance[i] })
	return parsed_input
