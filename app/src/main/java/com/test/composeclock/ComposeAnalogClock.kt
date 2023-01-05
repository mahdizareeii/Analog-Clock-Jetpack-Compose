package com.test.composeclock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import java.util.*

@Composable
fun ComposeAnalogClock(
    clockBackgroundColor: Color = Color.Black,
    clockThicknessColor: Color = Color.Gray,
    clockHourMarkersColor: Color = Color.White,
    clockMinuteMarkersColor: Color = Color.White,
    /** clock hand color **/
    hourHandColor: Color = Color.White,
    minuteHandColor: Color = Color.White,
    secondHandColor: Color = Color.Red,
    /** current time in milli seconds **/
    timeInMillis: () -> Long
) {
    //parsing time
    val date = Date(timeInMillis.invoke())
    val calendar = Calendar.getInstance().apply {
        time = date
    }
    val second = calendar.get(Calendar.SECOND)
    val minute = calendar.get(Calendar.MINUTE)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    Canvas(modifier = Modifier.fillMaxSize()) {
        //operate clock radius
        val circleRadius = (size.width / 2f)
        //find center of canvas
        val canvasCenter = Offset(x = size.width / 2f, y = size.height / 2f)
        //or use this :) val canvasCenter = center

        //step 1: draw clock background
        drawCircle(
            color = clockBackgroundColor,
            radius = circleRadius,
            center = canvasCenter
        )

        //step 2:draw clock thickness
        drawCircle(
            color = clockThicknessColor,
            style = Stroke(
                width = 10f
            ),
            center = canvasCenter
        )

        //step 3: draw clock minute markers
        val minuteMarkerLength = circleRadius / 20f
        repeat(60) {
            rotate((it / 60f) * 360) {
                val start = center - Offset(0f, circleRadius)
                val end = start + Offset(0f, minuteMarkerLength)
                drawLine(
                    color = clockMinuteMarkersColor,
                    start = start,
                    end = end,
                    strokeWidth = 2f
                )
            }
        }

        //step 4: draw clock hour markers
        val hourMarkerLength = circleRadius / 10f
        repeat(12) {
            rotate((it / 12f) * 360) {
                val start = center - Offset(0f, circleRadius)
                val end = start + Offset(0f, hourMarkerLength)
                drawLine(
                    color = clockHourMarkersColor,
                    start = start,
                    end = end,
                    strokeWidth = 8f
                )
            }
        }

        val secondRatio = second / 60f
        val minuteRatio = minute / 60f
        val hourRatio = (hour + minuteRatio) / 12f

        //step 5: draw clock hour hand
        rotate(hourRatio * 360) {
            drawLine(
                color = hourHandColor,
                start = center - Offset(0f, circleRadius * 0.5f),
                end = center,
                strokeWidth = 6f
            )
        }

        //step 6: draw clock minute hand
        rotate(minuteRatio * 360) {
            drawLine(
                color = minuteHandColor,
                start = center - Offset(0f, circleRadius * 0.8f),
                end = center,
                strokeWidth = 5f
            )
        }

        //step 7: draw clock second hand
        rotate(secondRatio * 360) {
            drawLine(
                color = secondHandColor,
                start = center - Offset(0f, circleRadius * 0.9f),
                end = center,
                strokeWidth = 4f
            )
        }
    }
}