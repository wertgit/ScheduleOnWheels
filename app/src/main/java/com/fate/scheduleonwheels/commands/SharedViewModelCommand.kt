package com.fate.scheduleonwheels.commands


sealed class SharedViewModelCommand {
    object OnGenerateSchedule: SharedViewModelCommand()
}