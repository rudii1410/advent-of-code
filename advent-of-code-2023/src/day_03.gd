extends SceneTree

var util = preload("res://src/util.gd").new()

func _init():
	var inputs = util.read_input("day_03.txt")
	var maxX = inputs.size()
	var maxY = inputs[0].length()
	part_one(inputs, maxX, maxY)
	part_two(inputs, maxX, maxY)
	
	quit()

func part_one(inputs: Array, maxX: int, maxY: int):
	var result = 0
	for x in range(maxX):
		var skip_y = -1
		for y in range(maxY):
			if (y <= skip_y):
				continue
			if (not inputs[x][y].is_valid_int()):
				continue
			var tmp = traverse_engine(inputs, x, y, maxX, maxY, { "end": y, "num": "", "has_adj": false })
			skip_y = tmp["end"]
			if (tmp["has_adj"]):
				result += tmp["num"].to_int()
	
	print(result)
	
func part_two(inputs: Array, maxX: int, maxY: int):
	var gear_adj = {}
	for x in range(maxX):
		var skip_y = -1
		for y in range(maxY):
			if (y <= skip_y):
				continue
			if (not inputs[x][y].is_valid_int()):
				continue
			var tmp = traverse_engine(inputs, x, y, maxX, maxY, { "end": y, "num": "", "has_adj": false, "gear_adj": {} })
			skip_y = tmp["end"]
			if (tmp["gear_adj"].has("symbol")):
				var idx = "%s,%s" % [tmp["gear_adj"]["x"], tmp["gear_adj"]["y"]]
				var tmp_gear_adj = []
				if (gear_adj.has(idx)):
					tmp_gear_adj = gear_adj[idx]
				tmp_gear_adj.append(tmp["num"].to_int())
				gear_adj[idx] = tmp_gear_adj
	
	var result = 0
	for keys in gear_adj:
		if (gear_adj[keys].size() >= 2):
			var tmp_result = 1
			for i in gear_adj[keys]:
				tmp_result *= i
			result += tmp_result

	print(result)

func traverse_engine(data: Array, x: int, y: int, maxX: int, maxY: int, result: Dictionary) -> Dictionary:
	if (result["has_adj"]):
		return result
	if (not is_valid_position(x, y, maxX, maxY)):
		return result
	if (not data[x][y].is_valid_int()):
		return result

	result["num"] = result["num"] + data[x][y]
	result["end"] = y
	result = traverse_engine(data, x, y + 1, maxX, maxY, result)
	var adj_symbol = get_adjacent_symbol(data, x, y, maxX, maxY)
	if (adj_symbol["symbol"] != ""):
		result["has_adj"] = true
	if (adj_symbol["symbol"] == "*"):
		result["gear_adj"] = adj_symbol
	
	return result

func get_adjacent_symbol(data: Array, x: int, y: int, maxX: int, maxY: int) -> Dictionary:
	if (is_valid_position(x - 1, y - 1, maxX, maxY) && is_valid_symbol(data[x - 1][y - 1])):
		return { "x": x - 1, "y": y - 1, "symbol": data[x - 1][y - 1]}
	elif (is_valid_position(x - 1, y, maxX, maxY) && is_valid_symbol(data[x - 1][y])):
		return { "x": x - 1, "y": y, "symbol": data[x - 1][y]}
	elif (is_valid_position(x - 1, y + 1, maxX, maxY) && is_valid_symbol(data[x - 1][y + 1])):
		return { "x": x - 1, "y": y + 1, "symbol": data[x - 1][y + 1]}
	elif (is_valid_position(x, y - 1, maxX, maxY) && is_valid_symbol(data[x][y - 1])):
		return { "x": x, "y": y - 1, "symbol": data[x][y - 1]}
	elif (is_valid_position(x, y + 1, maxX, maxY) && is_valid_symbol(data[x][y + 1])):
		return { "x": x, "y": y + 1, "symbol": data[x][y + 1]}
	elif (is_valid_position(x + 1, y - 1, maxX, maxY) && is_valid_symbol(data[x + 1][y - 1])):
		return { "x": x + 1, "y": y - 1, "symbol": data[x + 1][y - 1]}
	elif (is_valid_position(x + 1, y, maxX, maxY) && is_valid_symbol(data[x + 1][y])):
		return { "x": x + 1, "y": y, "symbol": data[x + 1][y]}
	elif (is_valid_position(x + 1, y + 1, maxX, maxY) && is_valid_symbol(data[x + 1][y + 1])):
		return { "x": x + 1, "y": y + 1, "symbol": data[x + 1][y + 1]}
	return { "x": -1, "y": -1, "symbol": "" }

func is_valid_symbol(str: String) -> bool:
	if (str.is_valid_int() || str == "."):
		return false
	else:
		return true

func is_valid_position(x: int, y: int, maxX: int, maxY: int) -> bool:
	if (x < 0 || y < 0 || x >= maxX || y >= maxY):
		return false
	else:
		return true
