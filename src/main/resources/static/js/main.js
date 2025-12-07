// Función para mostrar el formulario y redimensionar la tabla
function mostrarFormulario() {
    var listCol = document.getElementById('vacantes-list-col');
    var formCol = document.getElementById('vacantes-form-col');

    // 1. Reducir la columna de la lista: De col-12 a col-6
    listCol.classList.remove('col-12');
    listCol.classList.add('col-6');

    // 2. Mostrar la columna del formulario: Quitar la clase d-none
    formCol.classList.remove('d-none');
}

// Función para ocultar el formulario y restaurar la tabla
function ocultarFormulario() {
    var listCol = document.getElementById('vacantes-list-col');
    var formCol = document.getElementById('vacantes-form-col');

    // 1. Restaurar la columna de la lista: De col-6 a col-12
    listCol.classList.remove('col-6');
    listCol.classList.add('col-12');

    // 2. Ocultar la columna del formulario: Agregar la clase d-none
    formCol.classList.add('d-none');
}
// --- FUNCIONES PARA EL MÓDULO DE CONVENIOS ---

function mostrarFormularioConvenio() {
    // Referencias a las dos columnas por su ID
    var listCol = document.getElementById('convenios-list-col');
    var formCol = document.getElementById('convenios-form-col');

    if (listCol && formCol) {
        // 1. Reducir la columna de la lista: De col-12 a col-6
        listCol.classList.remove('col-12');
        listCol.classList.add('col-6');

        // 2. Mostrar la columna del formulario: Quitar la clase d-none
        formCol.classList.remove('d-none');
    }
}

function ocultarFormularioConvenio() {
    // Referencias a las dos columnas por su ID
    var listCol = document.getElementById('convenios-list-col');
    var formCol = document.getElementById('convenios-form-col');

    if (listCol && formCol) {
        // 1. Restaurar la columna de la lista: De col-6 a col-12
        listCol.classList.remove('col-6');
        listCol.classList.add('col-12');

        // 2. Ocultar la columna del formulario: Agregar la clase d-none
        formCol.classList.add('d-none');
    }
}

// Opcional: Si quieres que el formulario empiece visible, puedes quitar el d-none en el HTML
// y llamar a mostrarFormularioConvenio() en el window.onload.

// --- FUNCIONES PARA EL MÓDULO DE USUARIOS Y ROLES ---

function mostrarFormularioUsuario() {
    // Referencias a las dos columnas por su ID
    var listCol = document.getElementById('usuarios-list-col');
    var formCol = document.getElementById('usuarios-form-col');

    if (listCol && formCol) {
        // 1. Reducir la columna de la lista: De col-12 a col-6
        listCol.classList.remove('col-12');
        listCol.classList.add('col-6');

        // 2. Mostrar la columna del formulario: Quitar la clase d-none
        formCol.classList.remove('d-none');
    }
}

function ocultarFormularioUsuario() {
    // Referencias a las dos columnas por su ID
    var listCol = document.getElementById('usuarios-list-col');
    var formCol = document.getElementById('usuarios-form-col');

    if (listCol && formCol) {
        // 1. Restaurar la columna de la lista: De col-6 a col-12
        listCol.classList.remove('col-6');
        listCol.classList.add('col-12');

        // 2. Ocultar la columna del formulario: Agregar la clase d-none
        formCol.classList.add('d-none');
    }
}

// ... (El resto de tus funciones de Vacantes y Convenios deben ir aquí) ...

// Cargar convenios en tabla
async function cargarConvenios() {
    const res = await fetch("/convenios/api");
    const data = await res.json();

    const tbody = document.getElementById("convenios-table-body");
    tbody.innerHTML = "";

    data.forEach(c => {
        tbody.innerHTML += `
            <tr>
                <td>${c.folio}</td>
                <td>${c.tipoConvenio}</td>
                <td>${c.estatus}</td>
                <td>
                    <button onclick="editarConvenio(${c.id})" class="btn btn-sm btn-warning">Editar</button>
                    <button onclick="eliminarConvenio(${c.id})" class="btn btn-sm btn-danger">Eliminar</button>
                </td>
            </tr>
        `;
    });
}

document.addEventListener("DOMContentLoaded", cargarConvenios);

// Guardar o actualizar
document.getElementById("convenio-form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const convenio = {
        folio: document.getElementById("folio").value,
        tipoConvenio: document.getElementById("tipo_convenio").value,
        fechaInicio: document.getElementById("fecha_inicio").value,
        fechaFin: document.getElementById("fecha_fin").value,
        estatus: "activo",
        escuela: { id: 1 } // temporal: ID fijo
    };

    await fetch("/convenios/api", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(convenio)
    });

    cargarConvenios();
    alert("Convenio guardado");
});
