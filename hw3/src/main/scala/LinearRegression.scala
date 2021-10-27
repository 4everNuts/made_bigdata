import breeze.linalg.Vector
// args: data.csv data_test.csv results.txt
object LinearRegression extends App {
  import breeze.linalg._
  import breeze.stats._
  import java.io._
  val matrix:DenseMatrix[Double]=csvread(new File(args(0)),',')
  val matrix_test:DenseMatrix[Double]=csvread(new File(args(1)),',')


  println(matrix)
  val train_cutoff :Int = (matrix.rows * 0.8).toInt
  val x_train = matrix(0 to train_cutoff, 0 to -2).copy
  val y_train = matrix(0 to train_cutoff, -1).copy
  val x_val = matrix(train_cutoff + 1 to -1, 0 to -2).copy
  val y_val = matrix(train_cutoff + 1 to -1, -1).copy

  // данные сгенерены так, что y = a*x1 + b*x2 + c*x3 + d + np.random.randn()
  // Реальные значения: a = -1, b = 2, c = 3, d = 4
  // Мы попытаемся их предсказать
  var a, b, c, d:Double = 0
  //learning rate
  val alpha :Double = 0.001


  var y_pred :DenseVector[Double] = DenseVector.zeros[Double](y_train.size)
  // Непонятно, как умножать вектор и скаляр, будем использовать временные вектора для этого
  //var a_vec, b_vec, c_vec, d_vec :DenseVector[Double] = DenseVector.zeros[Double](y.size)

  def getValidationError(a:Double, b:Double, c:Double, d:Double): Double ={
    val a_vec = DenseVector.fill[Double](y_val.size)(a)
    val b_vec = DenseVector.fill[Double](y_val.size)(b)
    val c_vec = DenseVector.fill[Double](y_val.size)(c)
    val d_vec = DenseVector.fill[Double](y_val.size)(d)
    val y_pred = (x_val(::, 0) *:* a_vec) + (x_val(::, 1) *:* b_vec) + (x_val(::, 2) *:* c_vec) + d_vec
    mean((y_pred - y_val) *:* (y_pred - y_val))
  }

  // Тренировка
  for (i <- 0 to 5000) {
    val a_vec = DenseVector.fill[Double](y_train.size)(a)
    val b_vec = DenseVector.fill[Double](y_train.size)(b)
    val c_vec = DenseVector.fill[Double](y_train.size)(c)
    val d_vec = DenseVector.fill[Double](y_train.size)(d)
    y_pred = (x_train(::, 0) *:* a_vec) + (x_train(::, 1) *:* b_vec) + (x_train(::, 2) *:* c_vec) + d_vec

    val a_grad :Double = mean((y_pred - y_train) *:* x_train(::, 0))
    val b_grad :Double = mean((y_pred - y_train) *:* x_train(::, 1))
    val c_grad :Double = mean((y_pred - y_train) *:* x_train(::, 2))
    val d_grad :Double = mean(y_pred - y_train)
    a = a - alpha * a_grad
    b = b - alpha * b_grad
    c = c - alpha * c_grad
    d = d - alpha * d_grad
    if (i % 200 == 0) {
      val error: Double = getValidationError(a, b, c, d)
      println(s"Step $i, Val Error=$error")
    }
  }
  println(a, b, c, d)

  // Test
  val x_test = matrix_test(::, 0 to -2).copy
  val y_test = matrix_test(::, -1)
  def getTestError(a:Double, b:Double, c:Double, d:Double): Double ={
    val a_vec = DenseVector.fill[Double](y_test.size)(a)
    val b_vec = DenseVector.fill[Double](y_test.size)(b)
    val c_vec = DenseVector.fill[Double](y_test.size)(c)
    val d_vec = DenseVector.fill[Double](y_test.size)(d)
    val y_pred = (x_test(::, 0) *:* a_vec) + (x_test(::, 1) *:* b_vec) + (x_test(::, 2) *:* c_vec) + d_vec
    mean((y_pred - y_test) *:* (y_pred - y_test))
  }
  val error: Double = getTestError(a, b, c, d)
  println(s"Test Error=$error")

  // Записываем результаты в файл
  val pw = new PrintWriter(new File(args(2)))
  pw.write(s"a=$a, b=$b, c=$c, d=$d")
  pw.close

}
