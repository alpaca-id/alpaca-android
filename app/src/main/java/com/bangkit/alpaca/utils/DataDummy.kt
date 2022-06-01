package com.bangkit.alpaca.utils

import com.bangkit.alpaca.model.Flashcard

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
}