package com.ericsson.cifwk.avs



import org.junit.*
import grails.test.mixin.*

@TestFor(AvsFileController)
@Mock(AvsFile)
class AvsFileControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/avsFile/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.avsFileInstanceList.size() == 0
        assert model.avsFileInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.avsFileInstance != null
    }

    void testSave() {
        controller.save()

        assert model.avsFileInstance != null
        assert view == '/avsFile/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/avsFile/show/1'
        assert controller.flash.message != null
        assert AvsFile.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/avsFile/list'


        populateValidParams(params)
        def avsFile = new AvsFile(params)

        assert avsFile.save() != null

        params.id = avsFile.id

        def model = controller.show()

        assert model.avsFileInstance == avsFile
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/avsFile/list'


        populateValidParams(params)
        def avsFile = new AvsFile(params)

        assert avsFile.save() != null

        params.id = avsFile.id

        def model = controller.edit()

        assert model.avsFileInstance == avsFile
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/avsFile/list'

        response.reset()


        populateValidParams(params)
        def avsFile = new AvsFile(params)

        assert avsFile.save() != null

        // test invalid parameters in update
        params.id = avsFile.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/avsFile/edit"
        assert model.avsFileInstance != null

        avsFile.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/avsFile/show/$avsFile.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        avsFile.clearErrors()

        populateValidParams(params)
        params.id = avsFile.id
        params.version = -1
        controller.update()

        assert view == "/avsFile/edit"
        assert model.avsFileInstance != null
        assert model.avsFileInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/avsFile/list'

        response.reset()

        populateValidParams(params)
        def avsFile = new AvsFile(params)

        assert avsFile.save() != null
        assert AvsFile.count() == 1

        params.id = avsFile.id

        controller.delete()

        assert AvsFile.count() == 0
        assert AvsFile.get(avsFile.id) == null
        assert response.redirectedUrl == '/avsFile/list'
    }
}
