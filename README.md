# Guias_DWF_HP240512

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Caso 3 - Salario Neto</title>
    <style>
        body { background-color: #121212; color: #e0e0e0; font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 600px; margin: 0 auto; background-color: #1e1e1e; padding: 20px; border-radius: 8px; border-top: 4px solid #3f51b5; }
        h2 { color: #7986cb; }
        .resultado { background-color: #2c2c2c; padding: 15px; border-left: 4px solid #3f51b5; margin-top: 15px; }
        .error { background-color: #3b1c1c; padding: 15px; border-left: 4px solid #ef5350; margin-top: 15px; color: #ef5350; }
        input[type="number"] { width: 100%; padding: 8px; margin-bottom: 10px; background: #333; color: white; border: 1px solid #555; }
        input[type="submit"] { background-color: #3f51b5; color: white; padding: 10px 15px; border: none; cursor: pointer; }
        hr { border: 0; border-top: 1px solid #555; margin: 10px 0; }
        .descuento { color: #ef5350; } /* Letra roja para los descuentos */
    </style>
</head>
<body>
<div class="container">
    <h2>Caso 3: Calcular el Salario Neto</h2>
    
    <form method="POST">
        <label>Ingrese el salario base ($):</label>
        <input type="number" step="0.01" name="salario" required>
        <input type="submit" value="Calcular Descuentos">
    </form>

    <?php
    function calcularSalarioNeto($salarioBase) {
        // Calculamos los descuentos fijos de ley
        $descuentoISSS = $salarioBase * 0.03;
        $descuentoAFP = $salarioBase * 0.075;
        $rentaExtra = 0; // La iniciamos en 0 por si no aplica el descuento del 10%
        
        // Empezamos a restarle al salario base
        $salarioLiquido = $salarioBase - $descuentoISSS - $descuentoAFP;

        // Revisamos si gana mas de $1000 para aplicar el otro descuento
        if ($salarioBase > 1000) {
            $rentaExtra = $salarioBase * 0.10;
            $salarioLiquido = $salarioLiquido - $rentaExtra;
        }

        // Metemos todos los resultados en un arreglo para poder devolverlos todos juntos
        $desglose = [
            'isss' => $descuentoISSS,
            'afp' => $descuentoAFP,
            'renta' => $rentaExtra,
            'neto' => $salarioLiquido
        ];

        return $desglose;
    }

    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $sueldoIngresado = $_POST['salario'];
        
        // Validamos que el salario no sea negativo
        if ($sueldoIngresado < 0) {
            echo "<div class='error'><strong>Error:</strong> El salario base no puede ser un número negativo.</div>";
        } else {
            // Guardamos el arreglo que nos devuelve la función
            $miSalario = calcularSalarioNeto($sueldoIngresado);
            
            echo "<div class='resultado'>";
            echo "Salario Base ingresado: <strong>$" . number_format($sueldoIngresado, 2) . "</strong><br>";
            echo "<hr>";
            
            // Mostramos los descuentos jalando los datos del arreglo
            echo "<span class='descuento'>- Descuento ISSS (3%): $" . number_format($miSalario['isss'], 2) . "</span><br>";
            echo "<span class='descuento'>- Descuento AFP (7.5%): $" . number_format($miSalario['afp'], 2) . "</span><br>";
            
            // Solo mostramos el 10% si la funcion calculó que sí aplicaba (si es mayor a 0)
            if ($miSalario['renta'] > 0) {
                echo "<span class='descuento'>- Descuento Extra (10%): $" . number_format($miSalario['renta'], 2) . "</span><br>";
            }
            
            echo "<hr>";
            echo "Salario Neto final: <strong>$" . number_format($miSalario['neto'], 2) . "</strong>";
            echo "</div>";
        }
    }
    ?>
</div>
</body>
</html>
