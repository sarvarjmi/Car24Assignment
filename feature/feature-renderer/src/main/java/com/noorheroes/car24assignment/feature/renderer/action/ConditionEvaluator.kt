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
            else -> false
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
