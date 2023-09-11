package com.sidert.sidertmovil.v2.domain;

import com.sidert.sidertmovil.v2.domain.convertors.LocalDatetimeConverter;
import com.sidert.sidertmovil.v2.domain.daos.*;
import com.sidert.sidertmovil.v2.domain.entities.*;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.AutoMigrationSpec;

@Database(
        entities = {
                Amortizaciones.class,
                ArqueoCaja.class,
                Aval.class,
                AvalAuto.class,
                AvalInd.class,
                AvalRen.class,
                Cancelaciones.class,
                CarteraGrupo.class,
                CarteraIndividual.class,
                CatColonias.class,
                CatalogoCampanas.class,
                CentroCosto.class,
                CierreDia.class,
                ClienteInd.class,
                ClienteIndAuto.class,
                ClienteIndRen.class,
                CodigosOxxo.class,
                ConsultaCc.class,
                ConyugeInd.class,
                ConyugeIndAuto.class,
                ConyugeIndRen.class,
                ConyugeIntegrante.class,
                ConyugeIntegranteAuto.class,
                ConyugeIntegranteRen.class,
                CreditoGpo.class,
                CreditoGpoAuto.class,
                CreditoGpoRen.class,
                CreditoInd.class,
                CreditoIndAuto.class,
                CreditoIndRen.class,
                CroquisGpo.class,
                CroquisGpoAuto.class,
                CroquisGpoRen.class,
                CroquisInd.class,
                CroquisIndAuto.class,
                CroquisIndRen.class,
                DatosBeneficiarioGpo.class,
                DatosBeneficiarioGpoRen.class,
                DatosBeneficiarioInd.class,
                DatosBeneficiarioIndRen.class,
                DatosCreditoCampana.class,
                DatosCreditoCampanaGpo.class,
                DatosCreditoCampanaGpoRen.class,
                DatosEntregaCartera.class,
                DestinosCredito.class,
                Direccion.class,
                DireccionesAuto.class,
                DireccionesRen.class,
                Documentos.class,
                DocumentosClientes.class,
                DocumentosIntegrante.class,
                DocumentosIntegranteRen.class,
                DocumentosRen.class,
                DomicilioIntegrante.class,
                DomicilioIntegranteAuto.class,
                DomicilioIntegranteRen.class,
                EconomicosInd.class,
                EconomicosIndAuto.class,
                EconomicosIndRen.class,
                Estados.class,
                EstadosCiviles.class,
                Fichas.class,
                Geo.class,
                Geolocalizacion.class,
                GestionesVerDom.class,
                IdentificacionTipo.class,
                ImpresionesVencida.class,
                ImpresionesVigente.class,
                IntegrantesGpo.class,
                IntegrantesGpoAuto.class,
                IntegrantesGpoRen.class,
                Localidades.class,
                LoginReport.class,
                MediosContacto.class,
                MediosPagoOri.class,
                MiembrosGpo.class,
                MiembrosPagos.class,
                Municipios.class,
                NegocioInd.class,
                NegocioIndAuto.class,
                NegocioIndRen.class,
                NegocioIntegrante.class,
                NegocioIntegranteAuto.class,
                NegocioIntegranteRen.class,
                NivelesEstudios.class,
                Ocupaciones.class,
                OtrosDatosIntegrante.class,
                OtrosDatosIntegranteAuto.class,
                OtrosDatosIntegranteRen.class,
                Pagos.class,
                PagosInd.class,
                Parentescos.class,
                PlazosPrestamos.class,
                PoliticasPldInd.class,
                PoliticasPldIndAuto.class,
                PoliticasPldIndRen.class,
                PoliticasPldIntegrante.class,
                PoliticasPldIntegranteAuto.class,
                PoliticasPldIntegranteRen.class,
                Prestamos.class,
                PrestamosGpo.class,
                PrestamosInd.class,
                PrestamosToRenovar.class,
                Recibos.class,
                RecibosAgfCc.class,
                RecibosCc.class,
                RecuperacionRecibos.class,
                RecuperacionRecibosCc.class,
                ReferenciaInd.class,
                ReferenciaIndAuto.class,
                ReferenciaIndRen.class,
                ReimpresionVigente.class,
                ReporteSesiones.class,
                RespuestasGpo.class,
                RespuestasInd.class,
                RespuestasIndV.class,
                RespuestasIntegranteV.class,
                ResumenesGestion.class,
                Sectores.class,
                ServiciosSincronizados.class,
                Sincronizado.class,
                Solicitudes.class,
                SolicitudesAuto.class,
                SolicitudesRen.class,
                Soporte.class,
                Sucursales.class,
                SucursalesLocalidades.class,
                TelefonosCliente.class,
                TelefonosIntegrante.class,
                TelefonosIntegranteAuto.class,
                TelefonosIntegranteRen.class,
                Tickets.class,
                TrackerAsesor.class,
                VerificacionesDomiciliarias.class,
                ViviendaTipos.class
        },
        version = 1
)
@TypeConverters({
        LocalDatetimeConverter.class
})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AmortizacionesDao amortizacionesDao();

    public abstract ArqueoCajaDao arqueoCajaDao();

    public abstract AvalDao avalDao();

    public abstract AvalAutoDao avalAutoDao();

    public abstract AvalIndDao avalIndDao();

    public abstract AvalRenDao avalRenDao();

    public abstract CancelacionesDao cancelacionesDao();

    public abstract CarteraGrupoDao carteraGrupoDao();

    public abstract CarteraIndividualDao carteraIndividualDao();

    public abstract CatColoniasDao catColoniasDao();

    public abstract CatalogoCampanasDao catalogoCampanasDao();

    public abstract CentroCostoDao centroCostoDao();

    public abstract CierreDiaDao cierreDiaDao();

    public abstract ClienteIndDao clienteIndDao();

    public abstract ClienteIndAutoDao clienteIndAutoDao();

    public abstract ClienteIndRenDao clienteIndRenDao();

    public abstract CodigosOxxoDao codigosOxxoDao();

    public abstract ConsultaCcDao consultaCcDao();

    public abstract ConyugeIndDao conyugeIndDao();

    public abstract ConyugeIndAutoDao conyugeIndAutoDao();

    public abstract ConyugeIndRenDao conyugeIndRenDao();

    public abstract ConyugeIntegranteDao conyugeIntegranteDao();

    public abstract ConyugeIntegranteAutoDao conyugeIntegranteAutoDao();

    public abstract ConyugeIntegranteRenDao conyugeIntegranteRenDao();

    public abstract CreditoGpoDao creditoGpoDao();

    public abstract CreditoGpoAutoDao creditoGpoAutoDao();

    public abstract CreditoGpoRenDao creditoGpoRenDao();

    public abstract CreditoIndDao creditoIndDao();

    public abstract CreditoIndAutoDao creditoIndAutoDao();

    public abstract CreditoIndRenDao creditoIndRenDao();

    public abstract CroquisGpoDao croquisGpoDao();

    public abstract CroquisGpoAutoDao croquisGpoAutoDao();

    public abstract CroquisGpoRenDao croquisGpoRenDao();

    public abstract CroquisIndDao croquisIndDao();

    public abstract CroquisIndAutoDao croquisIndAutoDao();

    public abstract CroquisIndRenDao croquisIndRenDao();

    public abstract DatosBeneficiarioGpoDao datosBeneficiarioGpoDao();

    public abstract DatosBeneficiarioGpoRenDao datosBeneficiarioGpoRenDao();

    public abstract DatosBeneficiarioIndDao datosBeneficiarioIndDao();

    public abstract DatosBeneficiarioIndRenDao datosBeneficiarioIndRenDao();

    public abstract DatosCreditoCampanaDao datosCreditoCampanaDao();

    public abstract DatosCreditoCampanaGpoDao datosCreditoCampanaGpoDao();

    public abstract DatosCreditoCampanaGpoRenDao datosCreditoCampanaGpoRenDao();

    public abstract DatosEntregaCarteraDao datosEntregaCarteraDao();

    public abstract DestinosCreditoDao destinosCreditoDao();

    public abstract DireccionDao direccionDao();

    public abstract DireccionesAutoDao direccionesAutoDao();

    public abstract DireccionesRenDao direccionesRenDao();

    public abstract DocumentosDao documentosDao();

    public abstract DocumentosClientesDao documentosClientesDao();

    public abstract DocumentosIntegranteDao documentosIntegranteDao();

    public abstract DocumentosIntegranteRenDao documentosIntegranteRenDao();

    public abstract DocumentosRenDao documentosRenDao();

    public abstract DomicilioIntegranteDao domicilioIntegranteDao();

    public abstract DomicilioIntegranteAutoDao domicilioIntegranteAutoDao();

    public abstract DomicilioIntegranteRenDao domicilioIntegranteRenDao();

    public abstract EconomicosIndDao economicosIndDao();

    public abstract EconomicosIndAutoDao economicosIndAutoDao();

    public abstract EconomicosIndRenDao economicosIndRenDao();

    public abstract EstadosDao estadosDao();

    public abstract EstadosCivilesDao estadosCivilesDao();

    public abstract FichasDao fichasDao();

    public abstract GeoDao geoDao();

    public abstract GeolocalizacionDao geolocalizacionDao();

    public abstract GestionesVerDomDao gestionesVerDomDao();

    public abstract IdentificacionTipoDao identificacionTipoDao();

    public abstract ImpresionesVencidaDao impresionesVencidaDao();

    public abstract ImpresionesVigenteDao impresionesVigenteDao();

    public abstract IntegrantesGpoDao integrantesGpoDao();

    public abstract IntegrantesGpoAutoDao integrantesGpoAutoDao();

    public abstract IntegrantesGpoRenDao integrantesGpoRenDao();

    public abstract LocalidadesDao localidadesDao();

    public abstract LoginReportDao loginReportDao();

    public abstract MediosContactoDao mediosContactoDao();

    public abstract MediosPagoOriDao mediosPagoOriDao();

    public abstract MiembrosGpoDao miembrosGpoDao();

    public abstract MiembrosPagosDao miembrosPagosDao();

    public abstract MunicipiosDao municipiosDao();

    public abstract NegocioIndDao negocioIndDao();

    public abstract NegocioIndAutoDao negocioIndAutoDao();

    public abstract NegocioIndRenDao negocioIndRenDao();

    public abstract NegocioIntegranteDao negocioIntegranteDao();

    public abstract NegocioIntegranteAutoDao negocioIntegranteAutoDao();

    public abstract NegocioIntegranteRenDao negocioIntegranteRenDao();

    public abstract NivelesEstudiosDao nivelesEstudiosDao();

    public abstract OcupacionesDao ocupacionesDao();

    public abstract OtrosDatosIntegranteDao otrosDatosIntegranteDao();

    public abstract OtrosDatosIntegranteAutoDao otrosDatosIntegranteAutoDao();

    public abstract OtrosDatosIntegranteRenDao otrosDatosIntegranteRenDao();

    public abstract PagosDao pagosDao();

    public abstract PagosIndDao pagosIndDao();

    public abstract ParentescosDao parentescosDao();

    public abstract PlazosPrestamosDao plazosPrestamosDao();

    public abstract PoliticasPldIndDao politicasPldIndDao();

    public abstract PoliticasPldIndAutoDao politicasPldIndAutoDao();

    public abstract PoliticasPldIndRenDao politicasPldIndRenDao();

    public abstract PoliticasPldIntegranteDao politicasPldIntegranteDao();

    public abstract PoliticasPldIntegranteAutoDao politicasPldIntegranteAutoDao();

    public abstract PoliticasPldIntegranteRenDao politicasPldIntegranteRenDao();

    public abstract PrestamosDao prestamosDao();

    public abstract PrestamosGpoDao prestamosGpoDao();

    public abstract PrestamosIndDao prestamosIndDao();

    public abstract PrestamosToRenovarDao prestamosToRenovarDao();

    public abstract RecibosDao recibosDao();

    public abstract RecibosAgfCcDao recibosAgfCcDao();

    public abstract RecibosCcDao recibosCcDao();

    public abstract RecuperacionRecibosDao recuperacionRecibosDao();

    public abstract RecuperacionRecibosCcDao recuperacionRecibosCcDao();

    public abstract ReferenciaIndDao referenciaIndDao();

    public abstract ReferenciaIndAutoDao referenciaIndAutoDao();

    public abstract ReferenciaIndRenDao referenciaIndRenDao();

    public abstract ReimpresionVigenteDao reimpresionVigenteDao();

    public abstract ReporteSesionesDao reporteSesionesDao();

    public abstract RespuestasGpoDao respuestasGpoDao();

    public abstract RespuestasIndDao respuestasIndDao();

    public abstract RespuestasIndVDao respuestasIndVDao();

    public abstract RespuestasIntegranteVDao respuestasIntegranteVDao();

    public abstract ResumenesGestionDao resumenesGestionDao();

    public abstract SectoresDao sectoresDao();

    public abstract ServiciosSincronizadosDao serviciosSincronizadosDao();

    public abstract SincronizadoDao sincronizadoDao();

    public abstract SolicitudesDao solicitudesDao();

    public abstract SolicitudesAutoDao solicitudesAutoDao();

    public abstract SolicitudesRenDao solicitudesRenDao();

    public abstract SoporteDao soporteDao();

    public abstract SucursalesDao sucursalesDao();

    public abstract SucursalesLocalidadesDao sucursalesLocalidadesDao();

    public abstract TelefonosClienteDao telefonosClienteDao();

    public abstract TelefonosIntegranteDao telefonosIntegranteDao();

    public abstract TelefonosIntegranteAutoDao telefonosIntegranteAutoDao();

    public abstract TelefonosIntegranteRenDao telefonosIntegranteRenDao();

    public abstract TicketsDao ticketsDao();

    public abstract TrackerAsesorDao trackerAsesorDao();

    public abstract VerificacionesDomiciliariasDao verificacionesDomiciliariasDao();

    public abstract ViviendaTiposDao viviendaTiposDao();

}
