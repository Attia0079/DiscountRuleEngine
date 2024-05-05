def Qualifier1(order: String): Boolean ={
  true
}
def Qualifier2(order: String): Boolean ={
  false
}
def Qualifier3(order: String): Boolean ={
  false
}
def Qualifier4(order: String): Boolean ={
  true
}

def Calculator1(order: String): Double ={
  0.1
}
def Calculator2(order: String): Double ={
  0.2
}
def Calculator3(order: String): Double ={
  0.3
}
def Calculator4(order: String): Double ={
  0.4
}
def getDiscountRules(): List[(String => Boolean,String => Double)] = {
  List(
    (Qualifier1, Calculator1),
    (Qualifier2, Calculator2),
    (Qualifier3, Calculator3),
    (Qualifier4, Calculator4)
  )
}

def testfunction(order: String,rules: (String => Boolean, String => Double)): String = {
//    if (x) "Hello, yes!" else "no!"
  val filtered_list =  rules.fil
  "Hi!"
}