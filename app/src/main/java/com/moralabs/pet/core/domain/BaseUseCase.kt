package com.moralabs.pet.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class BaseUseCase() {
    open fun execute() : Flow<BaseResult<*>> {
        return flow {

        }
    }
}