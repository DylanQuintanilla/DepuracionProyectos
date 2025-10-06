package com.example.investigacionaplicada2.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.investigacionaplicada2.R
import com.example.investigacionaplicada2.adapters.ArtistAdapter
import com.example.investigacionaplicada2.models.Artista

class ArtistasFragment : Fragment(), ArtistAdapter.OnArtistClick {

    private lateinit var rvArtistas: RecyclerView
    private lateinit var adapter: ArtistAdapter
    private val dbRef = FirebaseDatabase.getInstance().reference.child("artists")
    private lateinit var searchView: SearchView
    private lateinit var listaOriginal: MutableList<Artista>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_artistas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvArtistas = view.findViewById(R.id.rvArtistas)
        searchView = view.findViewById(R.id.searchView)
        adapter = ArtistAdapter(mutableListOf(), this)
        rvArtistas.layoutManager = LinearLayoutManager(requireContext())
        rvArtistas.adapter = adapter

        escucharCambios()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filtrarPorNombre(query.orEmpty())
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarPorNombre(newText.orEmpty())
                return true
            }
        })
    }

    private fun escucharCambios() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<Artista>()
                for (hijo in snapshot.children) {
                    val artista = hijo.getValue(Artista::class.java)
                    if (artista != null) lista.add(artista)
                }
                adapter.setData(lista)
                listaOriginal = lista
            }
            override fun onCancelled(error: DatabaseError) {
                // Manejar error
            }
        })
    }

    private fun filtrarPorNombre(texto: String) {
        val filtrados = listaOriginal.filter { it.nombre?.contains(texto, ignoreCase = true) == true }
        adapter.setData(filtrados)
    }

    override fun onClick(artista: Artista) {
        // Navegar a detalle de artista (opcional)
    }
}