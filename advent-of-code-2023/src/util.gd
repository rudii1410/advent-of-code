extends SceneTree

func _init():
	pass

func read_input(fileName: String) -> Array:
	var inputs = []
	var f = FileAccess.open("res://src/%s" % fileName, FileAccess.READ)
	while not f.eof_reached():
		inputs.append(f.get_line())
	f.close()
	return inputs