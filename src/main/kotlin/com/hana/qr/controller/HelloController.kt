package com.hana.qr.controller

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayOutputStream

@Controller
@Slf4j
class HelloController {

    @GetMapping
    @ResponseBody
    fun hello(): String {
        return "<h3>hello world</h3>"
    }
    @ResponseBody
    @GetMapping("/qr")
    fun create() : ResponseEntity<ByteArray> {
        val height = 200
        val width = 200
        val url = "https://www.naver.com"


        // QR 코드 생성 힌트 설정
        val hintMap: MutableMap<EncodeHintType, Any> = HashMap()
        hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L // 오류 수정 레벨 설정

        // QR 코드 생성
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix: BitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height, hintMap)

        // 바이트 배열로 QR 코드 이미지 변환
        val outputStream = ByteArrayOutputStream()
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream)
        val qrCodeImage = outputStream.toByteArray()

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(qrCodeImage)
    }
}