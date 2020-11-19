#### UNIT TEST

- NetworkResultTest
  
  - Memastikan data yang dimasukkan di dalam NetworkResult.Success sesuai
  - Memastikan exception yang terjadi di dalam NetworkResult.Error memiliki message yang sama

- ApiServiceTest
  
  - Memastikan ketika api ditembak, data yang didapatkan sesuai dengan yang ada di resource
  - Testing dilakukan untuk :
    - Bentuk List dari Movie dan TvShow
    - Bentuk Single dari Movie dan TvShow

- FavoriteViewModelTest
  
  - Memastikan pagingData dari repository bisa dipakai di viewmodel dan tidak bernilai null

- MovieDetailViewModelTest
  
  - Memastikan ketika data masuk ke viewmodel, livedata movie tidak bernilai null, data sesuai dengan yang ada di resource, dan error message kosong
  - Memastikan ketika error masuk ke viewmodel, livedata movie bernilai null dan error message sesuai dengan exceptionnya
  - Memastikan ketika data yang dicari ditemukan di db, apa yang ada di viewmodel sesuai (state nya 1 ketika ada dan 0 ketika tidak)

- MovieListViewModelTest
  
  - Memastikan ketika data masuk ke viewmodel, livedata movies tidak bernilai null, data berupa list dengan ukuran 20, dan error message kosong
  - Memastikan ketika error masuk ke viewmodel, livedata movies tidak bernilai null, data berupa list berukuran 0, dan error message sesuai dengan exceptionnya

- TvShowDetailViewModelTest
  
  - Memastikan ketika data masuk ke viewmodel, livedata tvShow tidak bernilai null, data sesuai dengan yang ada di resource, dan error message kosong
  - Memastikan ketika error masuk ke viewmodel, livedata tvShow bernilai null dan error message sesuai dengan exceptionnya
  - Memastikan ketika data yang dicari ditemukan di db, apa yang ada di viewmodel sesuai (state nya 1 ketika ada dan 0 ketika tidak)

- TvShowListViewModelTest
  
  - Memastikan ketika data masuk ke viewmodel, livedata tvShows tidak bernilai null, data berupa list dengan ukuran 20, dan error message kosong
  - Memastikan ketika error masuk ke viewmodel, livedata tvShows tidak bernilai null, data berupa list berukuran 0, dan error message sesuai dengan exceptionnya

- MovieRepositoryTest
  
  - Memastikan fungsi ApiService getPopularMovie dan getMovieDetail bisa dijalankan (menggunakan mockk)
  - Memastikan data yang didapatkan dari fungsi getPopularMovie sesuai dengan yang ada di resource
  - Memastikan fungsi Database isItemWithIdExists berfungsi selayaknya dan memiliki hasil yang konsisten ketika dipanggil oleh repository

- TvShowRepositoryTest
  
  - Memastikan fungsi ApiService getPopularTvShow dan getTvShowDetail bisa dijalankan (menggunakan mockk)
  - Memastikan data yang didapatkan dari fungsi getTvShowMovie sesuai dengan yang ada di resource
  - Memastikan fungsi Database isItemWithIdExists berfungsi selayaknya dan memiliki hasil yang konsisten ketika dipanggil oleh repository

#### ANDROID TEST

- MovieFragmentTest
  
  - Memastikan ketika MovieListFragment dibuka, recycler view bisa discroll ke index terakhir
  - Memastikan ketika item recycler view di klik, halaman detail terbuka

- TvShowFragmentTest
  
  - Memastikan ketika TvShowListFragment dibuka, recycler view bisa discroll ke index terakhir
  - Memastikan ketika item recycler view di klik, halaman detail terbuka

- FavoriteFragmentTest
  
  - Memastikan fungsi favorite berjalan dan halaman favorite bisa dibuka