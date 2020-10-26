package game.exceptions

import java.lang.Exception

class GameRuleException(msg: String): Exception("Incorrect configuration: $msg")