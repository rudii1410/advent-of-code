extends SceneTree

var util = preload("res://src/util.gd").new()

func _init():
	var inputs = util.read_input("day_10.txt")
	#print(merge_map({ 1: { 3: [2, 3], 2: { 1: [3, 2] } } }, { 2: { 4: [3, 3] } }))
	print(parse_input(inputs))

func parse_input(inputs: Array) -> Dictionary:
	var n_rows = inputs.size()
	var n_cols = inputs[0].length()
	var result = {}
	var start_pos = []
	
	for i in n_rows:
		for j in n_cols:
			match inputs[i][j]:
				".":
					continue
				"S":
					start_pos = [i, j]
				_:
					#print("before: %s" % result)
					#print("new pipes: %s" % get_connected_pipes(i, j, inputs))
					result = merge_map(result, get_connected_pipes(i, j, inputs))
					#print("after: %s" % result)
					#print("")
					

	return { "start": start_pos, "map": result }

func map_pipe(char: String) -> Array:
	match char:
		"|":
			return ["n", "s"]
		"-":
			return ["e", "w"]
		"L":
			return ["n", "e"]
		"J":
			return ["n", "w"]
		"7":
			return ["s", "w"]
		"F":
			return ["s", "e"]
		_:
			return []

func get_connected_pipes(x: int, y: int, data: Array) -> Dictionary:
	var pos = [
		[x - 1, y], [x, y - 1], [x, y + 1], [x + 1, y]
	]
	var maxX = data.size()
	var maxY = data[0].length()
	var current_pipe = map_pipe(data[x][y])
	var result = {}
	for p in pos:
		if is_valid_position(p[0], p[1], maxX, maxY):
			var adj_pipe = map_pipe(data[p[0]][p[1]])
			if adj_pipe.size() == 0:
				continue
			#print("%s - %s" % [current_pipe, adj_pipe])
			if is_pipe_connected(current_pipe[1], adj_pipe[0]):
				if not result.has(x):
					result[x] = {}
				result[x][y] = p
	
	return result

func is_pipe_connected(a: String, b: String) -> bool:
	return (a == "s" && b == "n") || (a == "w" && b == "e")

func is_valid_position(x: int, y: int, maxX: int, maxY: int) -> bool:
	if (x < 0 || y < 0 || x >= maxX || y >= maxY):
		return false
	else:
		return true

func merge_map(a: Dictionary, b: Dictionary) -> Dictionary:
	#print("a: %s, b: %s" % [a.is_empty(), b.is_empty()])
	if a.is_empty():
		return b
	if b.is_empty():
		return a

	var result = a
	for first in b:
		if not result.has(first):
			result[first] = {}
		for second in b[first]:
			if not result[first].has(second):
				result[first][second] = b[first][second]
	return result
