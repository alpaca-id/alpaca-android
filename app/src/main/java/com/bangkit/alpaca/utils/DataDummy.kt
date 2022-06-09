package com.bangkit.alpaca.utils

import com.bangkit.alpaca.model.Flashcard
import com.bangkit.alpaca.model.WordLevel
import com.bangkit.alpaca.model.WordStage

object DataDummy {

    fun provideFlashcard(): List<Flashcard> = listOf(
        Flashcard(
            id = 1,
            title = "Pengertian Disleksia",
            message = "Disleksia adalah gangguan dalam proses belajar yang ditandai dengan kesulitan membaca, menulis, atau mengeja. Penderita disleksia akan kesulitan dalam mengidentifikasi kata-kata yang diucapkan dan mengubahnya menjadi huruf atau kalimat"
        ),
        Flashcard(
            id = 2,
            title = "Disleksia Memengaruhi Kecerdasan?",
            message = "Disleksia tergolong sebagai gangguan saraf pada bagian otak yang memproses bahasa. Kondisi ini dapat dialami oleh anak-anak atau orang dewasa. Meskipun disleksia menyebabkan kesulitan dalam belajar, penyakit ini tidak memengaruhi tingkat kecerdasan penderitanya."
        ),
        Flashcard(
            id = 3,
            title = "Penyebab Disleksia",
            message = "Riwayat disleksia gangguan belajar lain pada keluarga, kelahiran prematur atau terlahir dengan berat badan rendah, paparan nikotin, alkohol, NAPZA, atau infeksi pada masa kehamilan."
        ),
        Flashcard(
            id = 4,
            title = "Kapan Harus ke Dokter?",
            message = "Segera lakukan konsultasi ke dokter jika perkembangan kemampuan membaca dan menulis anak terlihat lambat, atau anak memperlihatkan gejala disleksia seperti yang telah disebutkan di atas. Jika tidak segera ditangani, kesulitan membaca yang ia alami dapat berlangsung hingga dewasa."
        ),
        Flashcard(
            id = 5,
            title = "Pengenalan Disleksia pada Anak",
            message = "Beri semangat pada anak agar berani membaca\n" +
                    "Bekerja sama dengan guru di sekolah\n" +
                    "Bicara dengan anak tentang kondisinya\n" +
                    "Batasi menonton televisi\n" +
                    "Bergabung dengan support group"
        )
    )

    fun providesWordOrderLevel(): List<WordLevel> = listOf(
        WordLevel(
            id = "L1",
            level = 1,
            wordStages = providesWords(),
            isComplete = false
        ),
        WordLevel(
            id = "L2",
            level = 2,
            wordStages = providesWords(),
            isComplete = false
        ),
        WordLevel(
            id = "L3",
            level = 3,
            wordStages = providesWords(),
            isComplete = false
        ),
        WordLevel(
            id = "L4",
            level = 4,
            wordStages = providesWords(),
            isComplete = false
        ),
        WordLevel(
            id = "L5",
            level = 5,
            wordStages = providesWords(),
            isComplete = false
        ),
        WordLevel(
            id = "L6",
            level = 6,
            wordStages = providesWords(),
            isComplete = false
        ),
        WordLevel(
            id = "L7",
            level = 7,
            wordStages = providesWords(),
            isComplete = false
        ),
        WordLevel(
            id = "L8",
            level = 8,
            wordStages = providesWords(),
            isComplete = false
        ),
        WordLevel(
            id = "L9",
            level = 9,
            wordStages = providesWords(),
            isComplete = false
        ),
        WordLevel(
            id = "L10",
            level = 10,
            wordStages = providesWords(),
            isComplete = false
        ),
    )

    private fun providesWords(): List<WordStage> = listOf(
        WordStage(
            id = "1a",
            stage = 1,
            word = "MEJA",
            isComplete = false
        ),
        WordStage(
            id = "1b",
            stage = 2,
            word = "BUKU",
            isComplete = false
        ),
        WordStage(
            id = "1c",
            stage = 3,
            word = "BAJU",
            isComplete = false
        ),
        WordStage(
            id = "1d",
            stage = 4,
            word = "IKAN",
            isComplete = false
        ),
        WordStage(
            id = "1e",
            stage = 5,
            word = "KAKI",
            isComplete = false
        ),
        WordStage(
            id = "1f",
            stage = 6,
            word = "MATA",
            isComplete = false
        ),
        WordStage(
            id = "1g",
            stage = 7,
            word = "BARU",
            isComplete = false
        ),
        WordStage(
            id = "1h",
            stage = 8,
            word = "SAYA",
            isComplete = false
        ),
        WordStage(
            id = "1i",
            stage = 9,
            word = "BATU",
            isComplete = false
        ),
        WordStage(
            id = "1j",
            stage = 10,
            word = "AYAM",
            isComplete = false
        )
    )
}