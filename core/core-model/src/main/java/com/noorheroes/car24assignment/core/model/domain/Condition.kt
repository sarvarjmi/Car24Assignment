package com.noorheroes.car24assignment.core.model.domain

import com.noorheroes.car24assignment.core.model.json.ConditionOperator

data class Condition(
    val field: String,
    val operator: ConditionOperator,
    val value: Any? = null
)
