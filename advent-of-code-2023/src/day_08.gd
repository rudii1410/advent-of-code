extends SceneTree

var util = preload("res://src/util.gd").new()

func _init():
	var inputs = util.read_input("day_08.txt")
	var initial_steps = inputs[0]
	var graph = parse_input(inputs)
	
	#print(graph)
	#print("aabbcc".right(1))
	#print("Part one: %d" % part_one(initial_steps, graph))
	#print(traverse_3(["11A", "22A"], initial_steps, graph))
	print(traverse_3(["SLA", "AAA", "LVA", "NPA", "GDA", "RCA"], initial_steps, graph))

func part_one(initial_steps: String, graph: Dictionary) -> int:
	var result = 0
	var steps = initial_steps
	var current = "AAA"
	while true:
		if steps == "":
			steps = initial_steps
		var direction = steps[0]
		steps = steps.substr(1)
		current = graph[current][direction]
		result += 1
		if current == "ZZZ":
			break

	return result

func gcd(a: int, b: int) -> int:
	if b == 0:
		return a
	return gcd(b, a % b)

func lcm(arr: Array) -> int:
	var result = -1
	
	for a in arr:
		if result == -1:
			result = a
			continue
		result = (a * result) / gcd(a, result)
	
	return result

func traverse_3(starts: Array, initial_steps: String, graph: Dictionary) -> int:
	var result = []
	result.resize(starts.size())
	for s in range(starts.size()):
		result[s] = traverse_4(starts[s], initial_steps, graph)

	print(lcm(result))
	return 0
	
func traverse_4(start: String, initial_steps: String, graph: Dictionary) -> int:
	var result = 0
	var steps = initial_steps
	var current = start
	while true:
		if steps == "":
			steps = initial_steps
		var direction = steps[0]
		steps = steps.substr(1)
		current = graph[current][direction]
		result += 1
		if current.right(1) == "Z":
			break

	return result

func parse_input(raw_input: Array) -> Dictionary:
	var result = {}
	var i = 2
	
	while i < raw_input.size():
		var tmp = raw_input[i].split(" = ")
		var key = tmp[0]
		var values = tmp[1]
		values = values.trim_prefix("(")
		values = values.trim_suffix(")")
		var branch = values.split(", ")

		result[key] = { "L": branch[0], "R": branch[1] }
		i += 1

	return result
