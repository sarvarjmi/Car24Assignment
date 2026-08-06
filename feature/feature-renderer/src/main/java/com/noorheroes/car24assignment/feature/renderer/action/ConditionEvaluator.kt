package com.noorheroes.car24assignment.feature.renderer.action

import com.noorheroes.car24assignment.core.model.domain.Component
import com.noorheroes.car24assignment.core.model.domain.Condition
import com.noorheroes.car24assignment.core.model.json.ConditionOperator

object ConditionEvaluator {

    fun evaluate(component: Component, condition: Condition): Boolean {
        val fieldValue = getFieldValue(component, condition.field)
        
        return when (condition.operator) {
            ConditionOperator.EQUALS -> fieldValue == condition.value
            ConditionOperator.NOT_EQUALS -> fieldValue != condition.value
            ConditionOperator.EXISTS -> fieldValue != null
            ConditionOperator.GREATER_THAN -> {
                val f = (fieldValue as? Number)?.toDouble() ?: 0.0
                val v = (condition.value as? Number)?.toDouble() ?: 0.0
                f > v
            }
            ConditionOperator.LESS_THAN -> {
                val f = (fieldValue as? Number)?.toDouble() ?: 0.0
                val v = (condition.value as? Number)?.toDouble() ?: 0.0
                f < v
            }
            ConditionOperator.IN -> (condition.value as? List<*>)?.contains(fieldValue) ?: false
            ConditionOperator.NOT_IN -> (condition.value as? List<*>)?.contains(fieldValue) == false
        }
    }

    private fun getFieldValue(component: Component, field: String): Any? {
        return when (field) {
            "selected" -> component.state?.selected
            "enabled" -> component.state?.enabled
            "expanded" -> component.state?.expanded
            "checked" -> component.state?.checked
            "loading" -> component.state?.loading
            else -> component.properties[field]
        }
    }
}
