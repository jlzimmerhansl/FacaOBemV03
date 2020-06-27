package com.example.facaobemv03;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.facaobemv03.Models.ProdutoModelo;
import com.example.facaobemv03.database.BdFacaOBemOpenHelper;
import com.example.facaobemv03.database.BdTableDoador;
import com.example.facaobemv03.database.BdTableProduto;
import com.example.facaobemv03.database.BdTableProdutoDetalhe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FacaOBemrContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.facaobemv03";
    public static final String DOADOR = "doador";
    public static final String PRODUTO = "produto";
    public static final String PRODUTODETALHE = "produtoDetalhe";

    public static final int URI_DOADOR = 100;
    public static final int URI_ID_DOADOR = 101;
    public static final int URI_ID_PRODUTO = 201;
    public static final int URI_PRODUTO = 200;
    public static final int URI_PRODUTOSelecao = 202;
    public static final int URI_ID_PRODUTODETALHE = 300;
    public static final int URI_PRODUTODETALHE = 301;

    public static final String Cursor_dir = "vnd.android.cursor.dir/";
    public static final String Cursor_item = "vnd.android.cursor.item/";

    public static final Uri ENDERECO_BASE = Uri.parse("content://" + AUTHORITY);

    public static final Uri ENDERECO_DOADOR = Uri.withAppendedPath(ENDERECO_BASE, DOADOR);
    public static final Uri ENDERECO_PRODUTO = Uri.withAppendedPath(ENDERECO_BASE, PRODUTO);
    public static final Uri ENDERECO_PRODUTODETALHE = Uri.withAppendedPath(ENDERECO_BASE, PRODUTODETALHE);



    private BdFacaOBemOpenHelper openHelper;

    private UriMatcher getUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, DOADOR, URI_DOADOR);
        uriMatcher.addURI(AUTHORITY, DOADOR + "/#", URI_ID_DOADOR);

        uriMatcher.addURI(AUTHORITY, PRODUTO, URI_PRODUTO);
        uriMatcher.addURI(AUTHORITY, PRODUTO + "/#", URI_ID_PRODUTO);

        uriMatcher.addURI(AUTHORITY, PRODUTODETALHE, URI_PRODUTODETALHE);
        uriMatcher.addURI(AUTHORITY, PRODUTODETALHE + "/#", URI_ID_PRODUTODETALHE);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        openHelper = new BdFacaOBemOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase bd = openHelper.getReadableDatabase();
        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)){
            case URI_DOADOR:
                return new BdTableDoador(bd).query(projection, selection, selectionArgs, null, null, sortOrder);
            case URI_ID_DOADOR:
                return new BdTableDoador(bd).query(projection, BdTableDoador._ID + "=?", new String[]{id}, null, null, sortOrder);
            case URI_PRODUTO:
                return new BdTableProduto(bd).query(projection, selection, selectionArgs, null, null, sortOrder);
            case URI_ID_PRODUTO:
                return new BdTableProduto(bd).query(projection, BdTableProduto._ID + "=?", new String[]{id}, null, null, sortOrder);
            case URI_PRODUTOSelecao:
                return new BdTableProduto(bd).query(projection, BdTableProduto._ID + "=" + BdTableDoador._ID, new String[]{id}, null, null, sortOrder);
            case URI_PRODUTODETALHE:
                return new BdTableProdutoDetalhe(bd).query(projection, selection, selectionArgs, null, null, sortOrder);
            case URI_ID_PRODUTODETALHE:
                return new BdTableProdutoDetalhe(bd).query(projection, BdTableProdutoDetalhe._ID + "=?", new String[]{id}, null, null, sortOrder);
            default:
                throw new UnsupportedOperationException("Endereco não suportado" + uri.getPath());
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (getUriMatcher().match(uri)){
            case URI_DOADOR:
                return Cursor_dir + DOADOR;
            case URI_ID_DOADOR:
                return Cursor_item + DOADOR;
            case URI_PRODUTO:
                return Cursor_dir + PRODUTO;
            case URI_ID_PRODUTO:
                return Cursor_item + PRODUTO;
            case URI_PRODUTODETALHE:
                return Cursor_item + PRODUTODETALHE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        long id;

        switch (getUriMatcher().match(uri)){
            case URI_DOADOR:
                id = new BdTableDoador(bd).insert(contentValues);
                break;
            case URI_PRODUTO:
                id = new BdTableProduto(bd).insert(contentValues);
                break;
            case URI_ID_PRODUTODETALHE:
                id = new BdTableProdutoDetalhe(bd).insert(contentValues);
            default:
                throw new UnsupportedOperationException("Endereco insert não suportado" + uri.getPath());
        }

        if (id == -1) {
            throw new SQLException("Nãp foi possivel inserir o registro");
        }

        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();
        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)){
            case URI_ID_DOADOR:
                return new BdTableDoador(bd).delete(BdTableDoador._ID + "=?", new String[]{id});
            case URI_ID_PRODUTO:
                return new BdTableProduto(bd).delete(BdTableProduto._ID + "=?", new String[]{id});
            case URI_ID_PRODUTODETALHE:
                return new BdTableProdutoDetalhe(bd).delete(BdTableProdutoDetalhe._ID + "=?", new String[]{id});
            default:
                throw new UnsupportedOperationException("Endereco delete não suportado" + uri.getPath());
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();
        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)){
            case URI_ID_DOADOR:
                return new BdTableDoador(bd).update(contentValues, BdTableDoador._ID + "=?", new String[]{id});
            case URI_ID_PRODUTO:
                return new BdTableProduto(bd).update(contentValues, BdTableProduto._ID + "=?", new String[]{id});
            case URI_ID_PRODUTODETALHE:
                return new BdTableProdutoDetalhe(bd).update(contentValues, BdTableProdutoDetalhe._ID + "=?", new String[]{id});
            default:
                throw new UnsupportedOperationException("Endereço update não suportado" + uri.getPath());
        }
    }
}
