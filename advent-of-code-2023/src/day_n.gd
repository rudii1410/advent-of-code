extends SceneTree

var util = preload("res://src/util.gd").new()

func _init():
	var inputs = util.read_input("dayn.txt")
	print(inputs)
	quit()
