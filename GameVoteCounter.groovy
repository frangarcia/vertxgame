import io.vertx.core.json.JsonObject

Map<String, Map<String, Long>> votesByUser = ["team1":[:], "team2":[:]]
Map<String, Long> totalVotes = ["team1":0, "team2":0]
vertx.eventBus().consumer("feedback", { message ->
	String type, id
	if (message.body() instanceof JsonObject) {
		type = message.body().getString('type')
		id = message.body()?.getString('id')
	} else {
		type = message.body().type
		id = message.body()?.id
	}
  if (!totalVotes.get(type)) {
    totalVotes.put(type, 1)
  } else {
    totalVotes.put(type, totalVotes.get(type) + 1)
  }
	if (!votesByUser?.get(type)?.get(id)) {
		votesByUser?.get(type)?.put(id, 1)
	} else {
		votesByUser?.get(type)?.put(id, (votesByUser?.get(type)?.get(id)+1))
	}
})

vertx.eventBus().consumer("votes", { message ->
  message.reply(new JsonObject(totalVotes).toString())
})

vertx.eventBus().consumer("standings", { message ->
	message.reply(new JsonObject(votesByUser).toString())
})
