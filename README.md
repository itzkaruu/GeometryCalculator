# GeometryCalculator
A Java Swing-based geometry calculator that computes volume, base area, top area, and total surface area for cones, cylinders, and conical frustums using an interactive GUI.

# 📐 Geometry Calculator (Java Swing)

A desktop-based **Geometry Calculator** built using Java Swing that allows users to compute measurements for 3D shapes such as:

- 🔺 Circular Cone  
- 🛢️ Circular Cylinder  
- 🔻 Conical Frustum  

---

## ✨ Features

- Interactive **GUI built with Java Swing**
- Supports multiple geometric shapes
- Calculates:
  - Volume
  - Base Area
  - Top Area
  - Total Surface Area
- Custom **numpad input system**
- Dynamic labels depending on selected shape
- Input validation with error handling
- Clean and responsive layout design

---

## 🧮 Supported Formulas

### Cone
- Volume = (1/3)πr²h  
- Base Area = πr²  
- Total Surface Area = πr² + πrs  

### Cylinder
- Volume = πr²h  
- Base Area = πr²  
- Top Area = πr²  
- Total Surface Area = 2πr² + 2πrh  

### Conical Frustum
- Volume = (1/3)πh(r₁² + r₁r₂ + r₂²)  
- Top Area = πr₂²  
- Total Surface Area = π(r₁² + r₂² + s(r₁ + r₂))  

---

## 🖥️ User Interface

- Radio buttons to select shape
- Input fields for:
  - Radius (r)
  - Height (h)
  - Slant height or top radius (s)
- Built-in numpad for controlled input
- Display panel for results and error messages

---

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/geometry-calculator.git
